package com.zsy.model.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class TopicVo implements Serializable {
    private Integer id;
    private String name;
    private String description;
    @JsonProperty("cover")
    private String coverImage;
    private SimpleUserVo user;
    private Date createAt;
    private Long timeStamp;

    public Long getTimeStamp() {
        return createAt.getTime();
    }

}
