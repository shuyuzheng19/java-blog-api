package com.zsy.model.pojo;

import lombok.Data;

import java.io.Serializable;

/**
 * 角色实体类
 *
 * 用于表示系统中的角色信息。
 *
 * @author  郑书宇
 * @create 2023/8/30 13:55
 */
@Data
public class Role implements Serializable {
    /**
     * 角色ID
     */
    private Integer id;

    /**
     * 角色名称
     */
    private String name;

    /**
     * 角色描述
     */
    private String description;
}
