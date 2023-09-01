package com.zsy.mapper;

import com.zsy.model.pojo.Blog;
import com.zsy.model.request.BlogAdminFilterRequest;
import com.zsy.model.request.BlogPagingRequest;
import com.zsy.model.vo.BlogAdminVo;
import com.zsy.model.vo.BlogContentVo;
import com.zsy.model.vo.BlogVo;
import com.zsy.model.vo.SimpleBlogVo;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * @author 郑书宇
 * @create 2023/8/30 19:54
 * @desc
 */
@Repository
public interface BlogMapper {
    List<BlogVo> findPagingCategoryBlog(BlogPagingRequest request);
    List<SimpleBlogVo> top10BlogList();
    List<SimpleBlogVo> findAllSimpleBlog();
    List<SimpleBlogVo> findAllSimpleBlog2();
    List<BlogVo> findUserPagingCategoryBlog(Integer uid);
    List<SimpleBlogVo> userTopBlog(Integer uid);
    List<SimpleBlogVo> rangeBlog(Date startDate,Date endDate);
    BlogContentVo getBlogById(Integer bid);
    int saveBlog(Blog blog);
    int updateBlog(Blog blog);
    int insertBlogTagAssociation(Integer bid,List<Integer> tids);
    int deleteBlogTagAssociation(Integer bid);
    List<BlogAdminVo> getUserBlogs(Integer uid,@Param("request") BlogAdminFilterRequest request,boolean deleted);
    int deleteBlog(Integer bid,boolean deleted,Integer uid);
    int deleteBlogs(List<Integer> bids,boolean deleted,Integer uid);
}
