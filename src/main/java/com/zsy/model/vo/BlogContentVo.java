package com.zsy.model.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author 郑书宇
 * @create 2023/8/31 0:43
 * @desc
 */
@Data
public class BlogContentVo implements Serializable {
    private Integer id;
    private String description;
    private String title;
    private String coverImage;
    private String sourceUrl;
    private String content;
    private Long eyeCount;
    private Long likeCount;
    private CategoryVo category;
    private List<TagVo> tags;
    private SimpleTopicVo topic;
    private SimpleUserVo user;
    @JsonProperty("createTime")
    private Date createAt;
    @JsonProperty("updateTime")
    private Date updateAt;
}
