package com.zsy.model.pojo;

import com.zsy.model.vo.*;
import lombok.Data;
import org.apache.tomcat.util.http.ResponseUtil;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author 郑书宇
 * @create 2023/8/30 19:57
 * @desc
 */
@Data
public class Blog implements Serializable {
    /**
     * 博客ID
     */
    private Integer id;

    /**
     * 博客描述
     */
    private String description;

    /**
     * 博客标题
     */
    private String title;

    /**
     * 博客封面图片
     */
    private String coverImage;

    /**
     * 原始链接
     */
    private String sourceUrl;

    /**
     * 博客内容
     */
    private String content;

    /**
     * 阅读数
     */
    private Long eyeCount;

    /**
     * 点赞数
     */
    private Long likeCount;

    /**
     * 创建时间
     */
    private Date createAt;

    /**
     * 更新时间
     */
    private Date updateAt;

    /**
     * 博客所属标签列表
     */
    private List<Tag> tags;

    /**
     * 博客所属分类ID
     */
    private Integer categoryId;

    /**
     * 博客所属分类
     */
    private Category category;

    /**
     * 博客作者ID
     */
    private Integer userId;

    /**
     * 博客作者
     */
    private User user;

    /**
     * 博客所属专题ID
     */
    private Integer topicId;

    /**
     * 博客所属专题
     */
    private Topic topic;

    /**
     * 被删除时间
     */
    private Date deletedAt;

//    public BlogVo toVo(){
//        BlogVo blogVo=new BlogVo();
//        blogVo.setId(id);
//        blogVo.setTitle(title);
//        blogVo.setDescription(description);
//        blogVo.setCoverImage(coverImage);
//        blogVo.setTimeStamp(createAt.getTime());
//        blogVo.setUser(user.toSimpleUserVo());
//        blogVo.setCategory(category.toVo());
//        return blogVo;
//    }
//
//
//    public BlogAdminVo toAdminBlogVo() {
//        BlogAdminVo adminVo = new BlogAdminVo();
//        adminVo.setId(this.getId());
//        adminVo.setTitle(this.getTitle());
//        adminVo.setDescription(this.getDescription());
//        adminVo.setCoverImage(this.getCoverImage());
//        if(this.topicId!=null) {
//            SimpleTopicVo topicVo = new SimpleTopicVo();
//            topicVo.setId(this.getTopic().getId());
//            topicVo.setName(this.getTopic().getName());
//            adminVo.setTopic(topicVo);
//        }else{
//            CategoryVo categoryVo = new CategoryVo();
//            categoryVo.setId(this.getCategory().getId());
//            categoryVo.setName(this.getCategory().getName());
//            adminVo.setCategory(categoryVo);
//            adminVo.setCreateAt(this.createAt);
//        }
//
//        adminVo.setEyeCount(this.getEyeCount());
//        adminVo.setLikeCount(this.getLikeCount());
//        adminVo.setOriginal(this.getSourceUrl() == null);
//        SimpleUserVo userVo = new SimpleUserVo();
//        userVo.setId(this.getUser().getId());
//        userVo.setNickName(this.getUser().getNickName());
//        adminVo.setUser(userVo);
//
//        return adminVo;
//    }
}
