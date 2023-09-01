package com.zsy.controller;

import com.zsy.constatns.CustomConstants;
import com.zsy.model.pojo.User;
import com.zsy.model.request.TopicRequest;
import com.zsy.model.response.Result;
import com.zsy.service.TagService;
import com.zsy.service.TopicService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @author 郑书宇
 * @create 2023/8/31 1:29
 * @desc
 */
@RequestMapping(CustomConstants.API_PREFIX+"/topics")
@RestController
public class TopicController {

    @Resource
    private TopicService topicService;

    @GetMapping("/list")
    public Result findTopicByPage(@RequestParam(defaultValue = "1") Integer page){
        return Result.SUCCESS(topicService.findPageTopic(page));
    }

    @GetMapping("/admin/all")
    public Result findAllTopic(HttpServletRequest request){
        if (!((User)request.getAttribute("user")).getRole().getName().equals("SUPER_ADMIN")) {
            return Result.CUSTOMIZE(403,"你没有权限访问");
        }
        return Result.SUCCESS(topicService.findAllTopic());
    }

    @GetMapping("/{tid}/blogs")
    public Result getTopicByPageList(@PathVariable("tid")Integer tid,@RequestParam(defaultValue = "1")Integer page){
        return Result.SUCCESS(topicService.getTopicBlogList(page,tid));
    }

    @GetMapping("/get/{tid}")
    public Result getTopicById(@PathVariable("tid")Integer tid){
        return Result.SUCCESS(topicService.getTopicById(tid));
    }

    @GetMapping("/user/{uid}/list")
    public Result getUserTopicList(@PathVariable("uid") Integer userId){
        return Result.SUCCESS(topicService.getUserTopicList(userId));
    }

    @GetMapping("/{tid}/list")
    public Result getTopicBlogList(@PathVariable("tid") Integer tid){
        return Result.SUCCESS(topicService.getTopicAllBlog(tid));
    }

    @GetMapping("/admin/current/list")
    public Result getCurrentUserTopicList(HttpServletRequest request){
        Integer userId = ((User)request.getAttribute("user")).getId();
        return Result.SUCCESS(topicService.getUserTopicList(userId));
    }

    @PostMapping("/admin/add")
    public Result addTopic(@RequestBody TopicRequest topicRequest,HttpServletRequest request){
        Integer userId = ((User)request.getAttribute("user")).getId();
        return Result.SUCCESS(topicService.addTopic(topicRequest,userId));
    }

}
