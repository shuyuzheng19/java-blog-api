package com.zsy.model.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author 郑书宇
 * @create 2023/8/30 21:53
 * @desc
 */
@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class SimpleBlogVo implements Serializable {
    private Integer id;
    private String title;
    @JsonProperty("desc")
    private String description;
    private String coverImage;
}
