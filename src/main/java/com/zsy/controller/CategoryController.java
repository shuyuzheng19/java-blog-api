package com.zsy.controller;

import com.zsy.constatns.CustomConstants;
import com.zsy.model.response.Result;
import com.zsy.service.CategoryService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author 郑书宇
 * @create 2023/8/31 1:29
 * @desc
 */
@RequestMapping(CustomConstants.API_PREFIX+"/category")
@RestController
public class CategoryController {

    @Resource
    private CategoryService categoryService;

    @GetMapping("/list")
    public Result getCategoryList(){
        return Result.SUCCESS(categoryService.getCategoryList());
    }

    @GetMapping("/list2")
    public Result getCategoryList2(){
        return Result.SUCCESS(categoryService.getCategoryListFormDB());
    }

    @PostMapping("/admin/add")
    public Result addCategory(String name){
        return Result.SUCCESS(categoryService.addCategory(name));
    }
}
