package com.zsy.model.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 郑书宇
 * @create 2023/8/30 21:26
 * @desc
 */
@Data
public class BlogVo implements Serializable {
    private Integer id;
    private String title;
    @JsonProperty("desc")
    private String description;
    private String coverImage;
    private long timeStamp;
    @JsonIgnore
    private Date createAt;
    private SimpleUserVo user;
    private CategoryVo category;

    public long getTimeStamp() {
        return createAt.getTime();
    }
}
