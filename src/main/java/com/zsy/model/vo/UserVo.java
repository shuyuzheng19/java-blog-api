package com.zsy.model.vo;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * @author 郑书宇
 * @create 2023/8/30 18:01
 * @desc
 */
@Data
public class UserVo implements Serializable {
    private Integer id;

    private String username;

    private String nickName;

    private String role;

    private String icon;
}

