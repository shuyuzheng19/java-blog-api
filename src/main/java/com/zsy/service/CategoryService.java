package com.zsy.service;

import com.zsy.model.pojo.Category;
import com.zsy.model.request.BlogAdminFilterRequest;
import com.zsy.model.request.CategoryFilterRequest;
import com.zsy.model.response.PageResult;
import com.zsy.model.vo.CategoryVo;

import java.util.List;

/**
 * @author 郑书宇
 * @create 2023/8/31 1:25
 * @desc
 */
public interface CategoryService {
    List<CategoryVo> getCategoryList();
    List<CategoryVo> getCategoryListFormDB();
    CategoryVo addCategory(String name);
    PageResult getAdminCategoryList(CategoryFilterRequest request);
    int updateCategory(CategoryVo categoryVo);
    int deleteCategory(Integer cid,boolean deleted);
    int deleteCategories(List<Integer> cids,boolean deleted);
}
