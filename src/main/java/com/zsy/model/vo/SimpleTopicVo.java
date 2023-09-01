package com.zsy.model.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author 郑书宇
 * @create 2023/8/31 0:45
 * @desc
 */
@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class SimpleTopicVo implements Serializable {
    private Integer id;
    private String name;
    private String description;
    @JsonProperty("cover")
    private String coverImage;
}
