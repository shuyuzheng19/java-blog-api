package com.zsy.model.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class BlogAdminVo implements Serializable {
    private Integer id;
    private String title;
    private String description;
    private String coverImage;
    private Long eyeCount;
    private Long likeCount;
    private CategoryVo category;
    private SimpleTopicVo topic;
    private Date createAt;
    private boolean original;
    private SimpleUserVo user;
}
