package com.zsy.model.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 郑书宇
 * @create 2023/8/31 21:38
 * @desc
 */
@Data
public class AdminOtherVo implements Serializable {
    private String id;
    private String name;
    private Date createAt;
    private Date updateAt;
    private boolean deleted;
}
