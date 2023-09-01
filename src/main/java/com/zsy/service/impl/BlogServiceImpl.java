package com.zsy.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zsy.config.ManualJacksonSerializationService;
import com.zsy.constatns.CustomConstants;
import com.zsy.constatns.PageConstants;
import com.zsy.constatns.RedisConstants;
import com.zsy.exception.CustomException;
import com.zsy.exception.ErrorEnum;
import com.zsy.mapper.BlogMapper;
import com.zsy.model.pojo.Blog;
import com.zsy.model.request.BlogAdminFilterRequest;
import com.zsy.model.request.BlogPagingRequest;
import com.zsy.model.request.BlogRequest;
import com.zsy.model.response.PageResult;
import com.zsy.model.vo.BlogAdminVo;
import com.zsy.model.vo.BlogContentVo;
import com.zsy.model.vo.BlogVo;
import com.zsy.model.vo.SimpleBlogVo;
import com.zsy.service.BlogService;
import com.zsy.service.search.MedilsearchClient;
import com.zsy.service.search.SearchResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import javax.annotation.PostConstruct;
import java.time.Duration;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author 郑书宇
 * @create 2023/8/30 21:25
 * @desc
 */
@Service
@RequiredArgsConstructor
public class BlogServiceImpl implements BlogService {

    private final BlogMapper blogMapper;

    private final RedisTemplate<String,String> redisTemplate;

    private final MedilsearchClient medilsearchClient;

    private final ManualJacksonSerializationService objectSerialization;

    @Value("${init.random}")
    private boolean initRandom;
    @Value("${init.search}")
    private boolean initSearch;

    @PostConstruct
    public void init() {
        medilsearchClient.init();
        List<SimpleBlogVo> blogs = null;
        if (initRandom) {
            this.initRandomBlog();
        }
        if (initSearch) {
            this.initSearch();
        }
    }

    @Override
    public PageResult findCategoryPagingBlog(BlogPagingRequest pageRequest) {
        PageHelper.startPage(pageRequest.getPage(), PageConstants.BLOG_LIST_PAGE_COUNT);
        PageInfo<BlogVo> pageInfo = PageInfo.of(blogMapper.findPagingCategoryBlog(pageRequest));
//        List<BlogVo> blogVoList = pageInfo.getList().stream().map(blog->blog.toVo()).collect(Collectors.toList());
        return PageResult.builder().page(pageInfo.getPageNum()).size(pageInfo.getPageSize()).total(pageInfo.getTotal()).data(pageInfo.getList()).build();
    }

    @Override
    public List<SimpleBlogVo> getHotBlog() {
        String hot = redisTemplate.opsForValue().get(RedisConstants.HOT_BLOG);
        if (Objects.isNull(hot)) {
            List<SimpleBlogVo> blogs = blogMapper.top10BlogList();
            redisTemplate.opsForValue().set(RedisConstants.HOT_BLOG, objectSerialization.serializeObject(blogs), RedisConstants.HOT_BLOG_EXPIRE);
            return blogs;
        } else {
            return objectSerialization.deserializeObject(hot,List.class);
        }
    }

    @Override
    public Set<SimpleBlogVo> randomBlog() {
        Set<String> strings = redisTemplate.opsForSet().distinctRandomMembers(RedisConstants.RANDOM_BLOG, PageConstants.RANDOM_BLOG_LIST_COUNT);
        Set<SimpleBlogVo> result = strings.stream().map(b->objectSerialization.deserializeObject(b,SimpleBlogVo.class)).collect(Collectors.toSet());
        return result;
    }

    @Override
    public List<SimpleBlogVo> getRecommendBlog() {
        String result = redisTemplate.opsForValue().get(RedisConstants.RECOMMEND_BLOG_KEY);
        if (Objects.isNull(result)) return new ArrayList<>();
        else return objectSerialization.deserializeObject(result,List.class);
    }

    @Override
    public PageResult findUserCategoryPagingBlog(int page, int uid) {
        if (uid <= 0) throw new CustomException(ErrorEnum.ERROR_USER_NOT_FOUND);
        PageHelper.startPage(page, PageConstants.BLOG_LIST_PAGE_COUNT);
        PageInfo<BlogVo> pageInfo = PageInfo.of(blogMapper.findUserPagingCategoryBlog(uid));
        return PageResult.builder().page(pageInfo.getPageNum()).size(pageInfo.getPageSize()).total(pageInfo.getTotal()).data(pageInfo.getList()).build();
    }

    @Override
    public List<SimpleBlogVo> getUserTopBlog(int uid) {
        if (uid <= 0) throw new CustomException(ErrorEnum.ERROR_USER_NOT_FOUND);
        return blogMapper.userTopBlog(uid);
    }

    @Override
    public Object searchBlog(String keyword, int page) {
        if (ObjectUtils.isEmpty(keyword)) throw new CustomException(ErrorEnum.ERROR_EMPTY);
        SearchResponse search = medilsearchClient.search(CustomConstants.SEARCH_BLOG_INDEX, keyword, page);
        return search.getHits();
    }

    @Override
    public PageResult getRangeBlog(Long start, Long end, int page) {
        Date startDate;
        Date endDate;
        if (start == null && end == null) {
            startDate = new Date(System.currentTimeMillis() - Duration.ofDays(30).toMillis());
            endDate = new Date();
        } else if (start > end) {
            throw new CustomException(30000, "开始时间不能大于结束时间");
        } else {
            startDate = new Date(start);
            endDate = new Date(end);
        }
        PageHelper.startPage(page, PageConstants.ARCHIVE_BLOG_LIST_COUNT);
        PageInfo<SimpleBlogVo> pageInfo = PageInfo.of(blogMapper.rangeBlog(startDate, endDate));
        return PageResult.builder().page(pageInfo.getPageNum()).size(pageInfo.getPageSize()).total(pageInfo.getTotal()).data(pageInfo.getList()).build();
    }

    @Override
    public BlogContentVo getBlogById(Integer bid) {
        if (ObjectUtils.isEmpty(bid)) throw new CustomException(ErrorEnum.ERROR_EMPTY);
        String idStr = String.valueOf(bid);
        Object blog = redisTemplate.opsForHash().get(RedisConstants.BLOG_MAP,idStr);
        if (Objects.isNull(blog)) {
            BlogContentVo result = blogMapper.getBlogById(bid);
            redisTemplate.opsForHash().put(RedisConstants.BLOG_MAP, idStr, objectSerialization.serializeObject(result));
            return result;
        } else {
            return objectSerialization.deserializeObject(blog.toString(),BlogContentVo.class);
        }
    }

    @Override
    public Long eyeCountSelfIncrement(String bid, Long defaultCount) {
        Object result = redisTemplate.opsForHash().get(RedisConstants.BLOG_EYE_COUNT_MAP_KEY, bid);
        if(Objects.isNull(result)) {
            defaultCount+=1;
            redisTemplate.opsForHash().put(RedisConstants.BLOG_EYE_COUNT_MAP_KEY, bid, defaultCount.toString());
            return defaultCount;
        }else{
            Long increment = redisTemplate.opsForHash().increment(RedisConstants.BLOG_EYE_COUNT_MAP_KEY, bid, 1);
            return increment;
        }
    }

    @Override
    public void initSearch() {
        List<SimpleBlogVo> blogs = blogMapper.findAllSimpleBlog();
        medilsearchClient.deleteAllDocument(CustomConstants.SEARCH_BLOG_INDEX);
        medilsearchClient.saveDocuments(CustomConstants.SEARCH_BLOG_INDEX, blogs);
    }

    @Override
    public void initRandomBlog() {
        redisTemplate.delete(RedisConstants.RANDOM_BLOG);
        List<SimpleBlogVo> blogs = blogMapper.findAllSimpleBlog2();
        List<String> blogsJson = blogs.stream().map(b->objectSerialization.serializeObject(b)).collect(Collectors.toList());
        redisTemplate.opsForSet().add(RedisConstants.RANDOM_BLOG,blogsJson.toArray(new String[]{}));
    }

    @Override
    @Transactional
    public void saveBlog(BlogRequest blogRequest, Integer uid) {
        blogRequest.check();
        Blog blog = blogRequest.toDo(uid);
        int count = blogMapper.saveBlog(blog);
        if(count==0){
            throw new CustomException(ErrorEnum.ERROR_SAVE_BLOG_FAIL);
        }
        if(blogRequest.getTags().size()>0){
            blogMapper.insertBlogTagAssociation(blog.getId(), blogRequest.getTags());
        }
        SimpleBlogVo simpleBlogVo = new SimpleBlogVo();
        simpleBlogVo.setId(blog.getId());
        simpleBlogVo.setDescription(blog.getDescription());
        simpleBlogVo.setTitle(blog.getTitle());
        medilsearchClient.saveDocuments(CustomConstants.SEARCH_BLOG_INDEX,simpleBlogVo);
        redisTemplate.opsForSet().add(RedisConstants.RANDOM_BLOG,objectSerialization.serializeObject(simpleBlogVo));
    }

    @Override
    public void updateBlog(BlogRequest blogRequest, Integer bid,Integer uid) {
        blogRequest.check();
        Blog blog = blogRequest.toDo(uid);
        blog.setId(bid);
        int count = blogMapper.updateBlog(blog);
        if(count==0){
            throw new CustomException(ErrorEnum.ERROR_SAVE_BLOG_FAIL);
        }
        if(blogRequest.getTags().size()>0){
            int count2 = blogMapper.deleteBlogTagAssociation(bid);
            if(count2>0){
                blogMapper.insertBlogTagAssociation(blog.getId(), blogRequest.getTags());
            }
        }
        redisTemplate.opsForHash().delete(RedisConstants.BLOG_MAP,String.valueOf(bid));
    }

    @Override
    public PageResult getUserAdminBlogs(Integer uid, BlogAdminFilterRequest request,boolean deleted) {
        PageHelper.startPage(request.getPage(), PageConstants.BLOG_MANAGER_COUNT);
        if(request.getStart()!=null&&request.getEnd()!=null) {
            request.setStartDate(new Date(request.getStart()));
            request.setEndDate(new Date(request.getEnd()));
        }
        PageInfo<BlogAdminVo> pageInfo = PageInfo.of(blogMapper.getUserBlogs(uid,request,deleted));
        return PageResult.builder().page(pageInfo.getPageNum()).size(pageInfo.getPageSize()).total(pageInfo.getTotal()).data(pageInfo.getList()).build();
    }

    @Override
    public int deleteBlog(Integer bid, boolean deleted,Integer uid) {
        if (bid <= 0) throw new CustomException(ErrorEnum.ERROR_USER_NOT_FOUND);
        int count = blogMapper.deleteBlog(bid, deleted,uid);
        return count;
    }


    @Override
    public int deleteBlogs(List<Integer> ids, boolean deleted,Integer uid) {
        if (ObjectUtils.isEmpty(ids)) throw new CustomException(ErrorEnum.ERROR_USER_NOT_FOUND);
        int count = blogMapper.deleteBlogs(ids, deleted,uid);
        return count;
    }
}
