package com.zsy.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zsy.config.ManualJacksonSerializationService;
import com.zsy.constatns.PageConstants;
import com.zsy.constatns.RedisConstants;
import com.zsy.exception.CustomException;
import com.zsy.exception.ErrorEnum;
import com.zsy.mapper.TagMapper;
import com.zsy.model.pojo.Category;
import com.zsy.model.pojo.Tag;
import com.zsy.model.request.BlogAdminFilterRequest;
import com.zsy.model.request.CategoryFilterRequest;
import com.zsy.model.response.PageResult;
import com.zsy.model.vo.AdminOtherVo;
import com.zsy.model.vo.BlogVo;
import com.zsy.model.vo.CategoryVo;
import com.zsy.model.vo.TagVo;
import com.zsy.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author 郑书宇
 * @create 2023/8/31 1:38
 * @desc
 */
@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {

    private final TagMapper tagMapper;

    private final StringRedisTemplate redisTemplate;

    private final ManualJacksonSerializationService objectSerializationService;

    @Override
    public Set<TagVo> getRandomTagList() {
        boolean flag = redisTemplate.hasKey(RedisConstants.RANDOM_TAG_LIST);
        if(!flag) {
            List<TagVo> tags = tagMapper.findAllTag();
            List<String> jsons = tags.stream().map(t->objectSerializationService.serializeObject(t)).collect(Collectors.toList());
            redisTemplate.opsForSet().add(RedisConstants.RANDOM_TAG_LIST,jsons.toArray(new String[]{}));
            redisTemplate.expire(RedisConstants.RANDOM_TAG_LIST,RedisConstants.RANDOM_TAG_LIST_EXPIRE);
        }
        Set<String> tagSts = redisTemplate.opsForSet().distinctRandomMembers(RedisConstants.RANDOM_TAG_LIST, PageConstants.RANDOM_TAG_LIST_COUNT);
        Set<TagVo> result = tagSts.stream().map(t->objectSerializationService.deserializeObject(t,TagVo.class)).collect(Collectors.toSet());
        return result;
    }

    @Override
    public List<TagVo> getAllTagListFormDB() {
        return tagMapper.findAllTag();
    }

    @Override
    public PageResult getTagIdBlogList(Integer tid,int page) {
        if(tid==null||tid<=0) throw new CustomException(ErrorEnum.ERROR_EMPTY);
        PageHelper.startPage(page, PageConstants.BLOG_LIST_PAGE_COUNT);
        PageInfo<BlogVo> pageInfo = PageInfo.of(tagMapper.findBlogByTagId(tid));
        return PageResult.builder().page(pageInfo.getPageNum()).size(pageInfo.getPageSize()).total(pageInfo.getTotal()).data(pageInfo.getList()).build();
    }

    @Override
    public TagVo getTagById(Integer tid) {
        if(tid==null||tid<=0) throw new CustomException(ErrorEnum.ERROR_EMPTY);
        return tagMapper.findById(tid);
    }

    @Override
    @Transactional
    public TagVo addTag(String name) {
        if(ObjectUtils.isEmpty(name)){
            throw new CustomException(ErrorEnum.ERROR_EMPTY);
        }
        Tag tag=new Tag();
        tag.setName(name);
        Date now = new Date();
        tag.setCreateAt(now);
        tag.setUpdateAt(now);
        int count = tagMapper.addTag(tag);
        if(count==0){
            throw new CustomException(ErrorEnum.ERROR_SAVE_TAG_FAIL);
        }
        System.out.println(tag);
        redisTemplate.delete(RedisConstants.RANDOM_TAG_LIST);
        TagVo tagVo=new TagVo();
        tagVo.setId(tag.getId());
        tagVo.setName(tag.getName());
        return tagVo;
    }

    @Override
    public PageResult getAdminCategoryList(CategoryFilterRequest request) {
        PageHelper.startPage(request.getPage(), PageConstants.TAG_LIST_COUNT);
        if(request.getStart()!=null&&request.getEnd()!=null) {
            request.setStartDate(new Date(request.getStart()));
            request.setEndDate(new Date(request.getEnd()));
        }
        PageInfo<AdminOtherVo> pageInfo = PageInfo.of(tagMapper.listTag(request));
        return PageResult.builder().page(pageInfo.getPageNum()).size(pageInfo.getPageSize()).total(pageInfo.getTotal()).data(pageInfo.getList()).build();
    }

    @Override
    public int updateTag(TagVo tagVo) {
        if(tagVo.getId()==null || ObjectUtils.isEmpty(tagVo.getName())){
            throw new CustomException(ErrorEnum.ERROR_EMPTY);
        }
        return tagMapper.updateTag(tagVo);
    }

    @Override
    public int deleteTag(Integer cid, boolean deleted) {
        if(cid<0){
            throw new CustomException(ErrorEnum.ERROR_EMPTY);
        }
        return tagMapper.deleteTag(cid,deleted);
    }

    @Override
    public int deleteTags(List<Integer> cids, boolean deleted) {
        if(ObjectUtils.isEmpty(cids)){
            throw new CustomException(ErrorEnum.ERROR_EMPTY);
        }
        return tagMapper.deleteTags(cids,deleted);
    }
}
