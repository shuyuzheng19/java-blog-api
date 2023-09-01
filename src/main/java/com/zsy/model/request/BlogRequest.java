package com.zsy.model.request;

import com.zsy.exception.CustomException;
import com.zsy.model.pojo.Blog;
import com.zsy.utils.ValidateUtils;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class BlogRequest {
    private String title;
    private String description;
    private String content;
    private String sourceUrl;
    private String coverImage;
    private List<Integer> tags;
    private Integer topic;
    private Integer category;

    public void check() {
        int titleLen = title.codePointCount(0, title.length());
        int descLen = description.codePointCount(0, description.length());
        if (titleLen < 1 || titleLen > 50) {
            throw new CustomException(10000,"博客标题不能小于1个字符并且不能大于50个字符");
        } else if (descLen < 1 || descLen > 200) {
            throw new CustomException(10000,"博客简介不能小于1个字符并且不能大于200个字符");
        } else if (content.isEmpty()) {
            throw new CustomException(10000,"博客内容不能为空");
        } else if (!ValidateUtils.isImageUrl(coverImage)) {
            throw new CustomException(10000,"这不是一个图片链接");
        }
    }

    public Blog toDo(Integer uid){
        Date now = new Date();
        Blog blog=new Blog();
        blog.setDescription(description);
        blog.setTitle(title);
        blog.setCoverImage(coverImage);
        blog.setSourceUrl(sourceUrl);
        blog.setContent(content);
        blog.setEyeCount(0L);
        blog.setLikeCount(0L);
        blog.setCreateAt(now);
        blog.setUpdateAt(now);
        blog.setUserId(uid);
        if(topic==null || topic<=0) {
            blog.setCategoryId(category);
        }else{
            blog.setTopicId(topic);
        }
        return blog;
    }
}
