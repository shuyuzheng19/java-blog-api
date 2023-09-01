package com.zsy.controller;

import com.zsy.constatns.CustomConstants;
import com.zsy.model.pojo.User;
import com.zsy.model.request.TopicRequest;
import com.zsy.model.response.Result;
import com.zsy.service.CategoryService;
import com.zsy.service.TagService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @author 郑书宇
 * @create 2023/8/31 1:29
 * @desc
 */
@RequestMapping(CustomConstants.API_PREFIX+"/tags")
@RestController
public class TagController {

    @Resource
    private TagService tagService;

    @GetMapping("/random")
    public Result getTagList(){
        return Result.SUCCESS(tagService.getRandomTagList());
    }

    @GetMapping("/list")
    public Result getTagListFormDb(){
        return Result.SUCCESS(tagService.getAllTagListFormDB());
    }

    @GetMapping("/{tid}/blogs")
    public Result getTagIdBlogList(@PathVariable("tid") Integer tid,@RequestParam(defaultValue = "1") Integer page){
        return Result.SUCCESS(tagService.getTagIdBlogList(tid,page));
    }

    @GetMapping("/get/{tid}")
    public Result getTagById(@PathVariable("tid") Integer tid){
        return Result.SUCCESS(tagService.getTagById(tid));
    }

    @PostMapping("/admin/add")
    public Result addTopic(String name){
        return Result.SUCCESS(tagService.addTag(name));
    }
}
