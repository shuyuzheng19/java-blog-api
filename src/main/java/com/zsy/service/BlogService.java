package com.zsy.service;

import com.zsy.model.request.BlogAdminFilterRequest;
import com.zsy.model.request.BlogPagingRequest;
import com.zsy.model.request.BlogRequest;
import com.zsy.model.response.PageResult;
import com.zsy.model.vo.BlogContentVo;
import com.zsy.model.vo.SimpleBlogVo;

import java.util.List;
import java.util.Set;

/**
 * @author 郑书宇
 * @create 2023/8/30 21:24
 * @desc
 */
public interface BlogService {
    PageResult findCategoryPagingBlog(BlogPagingRequest pageRequest);
    List<SimpleBlogVo> getHotBlog();
    Set<SimpleBlogVo> randomBlog();
    List<SimpleBlogVo> getRecommendBlog();
    PageResult findUserCategoryPagingBlog(int page,int uid);
    List<SimpleBlogVo> getUserTopBlog(int uid);
    Object searchBlog(String keyword,int page);
    PageResult getRangeBlog(Long start,Long end,int page);
    BlogContentVo getBlogById(Integer bid);
    Long eyeCountSelfIncrement(String bid,Long defaultCount);
    void initSearch();
    void initRandomBlog();
    void saveBlog(BlogRequest blogRequest,Integer uid);
    void updateBlog(BlogRequest blogRequest,Integer bid,Integer uid);
    PageResult getUserAdminBlogs(Integer uid, BlogAdminFilterRequest request,boolean deleted);
    int deleteBlog(Integer bid,boolean deleted,Integer uid);
    int deleteBlogs(List<Integer> bids,boolean deleted,Integer uid);
}
