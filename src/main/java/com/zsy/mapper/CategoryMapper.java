package com.zsy.mapper;

import com.zsy.model.pojo.Category;
import com.zsy.model.request.BlogAdminFilterRequest;
import com.zsy.model.request.CategoryFilterRequest;
import com.zsy.model.vo.AdminOtherVo;
import com.zsy.model.vo.CategoryVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author 郑书宇
 * @create 2023/8/31 1:22
 * @desc
 */
public interface CategoryMapper {
    List<CategoryVo> findAllCategory();
    int addCategory(Category category);
    List<AdminOtherVo> listCategory(@Param("request") CategoryFilterRequest request);
    int updateCategory(CategoryVo categoryVo);
    int deleteCategory(Integer cid,boolean deleted);
    int deleteCategories(List<Integer> cids,boolean deleted);
}
