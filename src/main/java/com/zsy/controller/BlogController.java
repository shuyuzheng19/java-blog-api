package com.zsy.controller;

import com.zsy.config.ManualJacksonSerializationService;
import com.zsy.constatns.CustomConstants;
import com.zsy.exception.CustomException;
import com.zsy.exception.ErrorEnum;
import com.zsy.model.request.BlogPagingRequest;
import com.zsy.model.response.Result;
import com.zsy.model.vo.BlogContentVo;
import com.zsy.service.BlogService;
import com.zsy.utils.IpUtils;
import com.zsy.utils.OtherUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 * @author 郑书宇
 * @create 2023/8/30 21:41
 * @desc
 */
@RequestMapping(CustomConstants.API_PREFIX+"/blog")
@RestController
@Slf4j
public class BlogController {

    @Resource
    private BlogService blogService;

    @Resource
    private ManualJacksonSerializationService manualJacksonSerializationService;

    @GetMapping("/list")
    public Result getCategoryPagingBlogList(@ModelAttribute BlogPagingRequest request){
        return Result.SUCCESS(blogService.findCategoryPagingBlog(request));
    }

    @GetMapping("/hots")
    public Result hotsBlog(){
        return Result.SUCCESS(blogService.getHotBlog());
    }

    @GetMapping("/random")
    public Result randomBlog(){
        return Result.SUCCESS(blogService.randomBlog());
    }

    @GetMapping("/recommend")
    public Result recommendBlog(){
        return Result.SUCCESS(blogService.getRecommendBlog());
    }

    @GetMapping("/user/{uid}")
    public Result getUserBlog(@PathVariable("uid") Integer uid,@RequestParam(defaultValue = "1") Integer page){
        return Result.SUCCESS(blogService.findUserCategoryPagingBlog(page,uid));
    }

    @GetMapping("/user/top/{uid}")
    public Result getUserTopBlog(@PathVariable("uid") Integer uid){
        return Result.SUCCESS(blogService.getUserTopBlog(uid));
    }

    @GetMapping("/search")
    public Result searchBlog(String keyword,@RequestParam(defaultValue = "1") Integer page){
        return Result.SUCCESS(blogService.searchBlog(keyword,page));
    }

    @GetMapping("/similar")
    public Result similarBlog(String keyword){
        return Result.SUCCESS(blogService.searchBlog(keyword,1));
    }

    @GetMapping("/range")
    public Result rangeBlog(@RequestParam(defaultValue = "1")Integer page,Long start,Long end){
        return Result.SUCCESS(blogService.getRangeBlog(start,end,page));
    }

    @GetMapping("/get/{id}")
    public void getBlogById(@PathVariable("id") Integer bid, HttpServletRequest request, HttpServletResponse response){
        BlogContentVo blog = blogService.getBlogById(bid);
        if(blog==null){
            throw new CustomException(ErrorEnum.ERROR_NOT_FOUND_BLOG);
        }
        blog.setEyeCount(blogService.eyeCountSelfIncrement(bid.toString(),blog.getEyeCount()));
        manualJacksonSerializationService.writeJsonToResponse(Result.SUCCESS(blog),response);
        String ip =  IpUtils.getIpAddress(request);
        log.info("用户查看博客: id:{}, ip:{}, city:{}",bid,ip,IpUtils.getIpCity(ip));
    }
}
