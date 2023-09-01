package com.zsy.model.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class SimpleUserVo implements Serializable {
    private Integer id;
    private String nickName;
}
