package com.zsy.model.pojo;

import com.zsy.model.vo.TopicVo;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author 郑书宇
 * @create 2023/8/30 20:00
 * @desc
 */
@Data
public class Topic {

    /**
     * 主题ID。
     */
    private int id;

    /**
     * 主题名称。
     */
    private String name;

    /**
     * 主题描述。
     */
    private String description;

    /**
     * 主题封面。
     */
    private String cover;

    /**
     * 最后更新时间。
     */
    private Date updateAt;

    /**
     * 创建时间。
     */
    private Date createAt;

    /**
     * 删除时间。
     */
    private Date deleteAt;

    /**
     * 用户ID。
     */
    private Integer userId;

    /**
     * 用户实体。
     */
    private User user;

}
