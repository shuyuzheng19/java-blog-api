package com.zsy.model.pojo;

import lombok.Data;

import java.util.Date;

/**
 * 表示一个标签。
 * <p>
 * 该类包含了关于一个标签的信息，包括它的ID、名称、创建时间、更新时间和删除时间。
 * </p>
 *
 * @author  郑书宇
 * @create 2023/8/30 19:59
 */
@Data
public class Tag {

    /**
     * 标签的唯一标识符。
     */
    private Integer id;

    /**
     * 标签的名称。
     */
    private String name;

    /**
     * 标签的创建日期和时间。
     */
    private Date createAt;

    /**
     * 标签的最后更新日期和时间。
     */
    private Date updateAt;

    /**
     * 标签的删除日期和时间。
     */
    private Date deleteAt;
}
