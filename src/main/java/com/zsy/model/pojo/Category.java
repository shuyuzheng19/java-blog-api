package com.zsy.model.pojo;

import com.zsy.model.vo.CategoryVo;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 表示一个分类。
 * <p>
 * 该类包含了关于一个分类的信息，包括它的ID、名称、创建时间、更新时间和删除时间。
 * </p>
 *
 * @author  郑书宇
 * @create 2023/8/30 19:57
 */
@Data
public class Category implements Serializable {

    /**
     * 分类的唯一标识符。
     */
    private Integer id;

    /**
     * 分类的名称。
     */
    private String name;

    /**
     * 分类的创建日期和时间。
     */
    private Date createAt;

    /**
     * 分类的最后更新日期和时间。
     */
    private Date updateAt;

    /**
     * 分类的删除日期和时间。
     */
    private Date deleteAt;

    public CategoryVo toVo(){
        CategoryVo categoryVo=new CategoryVo();
        categoryVo.setId(id);
        categoryVo.setName(name);
        return categoryVo;
    }
}
