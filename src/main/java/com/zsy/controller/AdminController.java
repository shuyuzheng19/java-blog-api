package com.zsy.controller;

import com.zsy.constatns.CustomConstants;
import com.zsy.model.pojo.User;
import com.zsy.model.request.BlogAdminFilterRequest;
import com.zsy.model.request.BlogRequest;
import com.zsy.model.request.CategoryFilterRequest;
import com.zsy.model.response.Result;
import com.zsy.model.vo.BlogContentVo;
import com.zsy.model.vo.CategoryVo;
import com.zsy.model.vo.TagVo;
import com.zsy.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author 郑书宇
 * @create 2023/8/31 11:04
 * @desc
 */
@RequestMapping(CustomConstants.API_PREFIX+"/admin")
@RestController
@RequiredArgsConstructor
public class AdminController {

    private final ChatGptService chatGptService;

    private final AdminService adminService;

    private final BlogService blogService;

    private final CategoryService categoryService;

    private final TagService tagService;

    @GetMapping(value = "/chat", produces = "text/event-stream;charset=UTF-8")
    public SseEmitter chatGptHelper(String message) throws IOException {
        return chatGptService.chat(message);
    }

    @GetMapping("/get_edit")
    public Result getUserEditBlog(HttpServletRequest request){
        int uid = ((User)request.getAttribute("user")).getId();
        return Result.SUCCESS(adminService.getSaveUserEditor(uid));
    }

    @PostMapping("/save_edit")
    public Result saveUserEditBlog(HttpServletRequest request,@RequestBody Map<String,String> map){
        String content = map.get("content");
        if(ObjectUtils.isEmpty(content)){
            return Result.CUSTOMIZE(10000,"要保存的文章不能为空");
        }
        int uid = ((User)request.getAttribute("user")).getId();
        adminService.saveUserEditBlog(uid,content);
        return Result.OK;
    }

    @PostMapping("/save")
    public Result saveBlog(@RequestBody BlogRequest blogRequest,HttpServletRequest request){
        int uid = ((User)request.getAttribute("user")).getId();
        blogService.saveBlog(blogRequest,uid);
        return Result.OK;
    }

    @PostMapping("/update_blog/{bid}")
    public Result updateBlogById(@PathVariable("bid") Integer bid,@RequestBody BlogRequest blogRequest,HttpServletRequest request) {
        int uid = ((User)request.getAttribute("user")).getId();
        blogService.updateBlog(blogRequest,bid,uid);
        return Result.OK;
    }

    @GetMapping("/edit/{bid}")
    public Result getUpdateBlogById(@PathVariable("bid") Integer bid){
        BlogContentVo blog = blogService.getBlogById(bid);
        BlogRequest blogRequest=new BlogRequest();
        blogRequest.setTitle(blog.getTitle());
        blogRequest.setDescription(blog.getDescription());
        blogRequest.setContent(blog.getContent());
        blogRequest.setSourceUrl(blog.getSourceUrl());
        blogRequest.setCoverImage(blog.getCoverImage());
        CategoryVo category = blog.getCategory();
        if(category!=null) {
            blogRequest.setCategory(category.getId());
            blogRequest.setTags(blog.getTags().stream().map(t -> t.getId()).collect(Collectors.toList()));
        }else{
            blogRequest.setTopic(blog.getTopic().getId());
        }
        return Result.SUCCESS(blogRequest);
    }

    @GetMapping("/me/blog")
    public Result getCurrentUserBlogs(@ModelAttribute BlogAdminFilterRequest filterRequest,HttpServletRequest request){
        int uid = ((User)request.getAttribute("user")).getId();
        return Result.SUCCESS(blogService.getUserAdminBlogs(uid,filterRequest,false));
    }

    @GetMapping("/me/delete_blog")
    public Result getCurrentDeleteUserBlogs(@ModelAttribute BlogAdminFilterRequest filterRequest,HttpServletRequest request){
        int uid = ((User)request.getAttribute("user")).getId();
        return Result.SUCCESS(blogService.getUserAdminBlogs(uid,filterRequest,true));
    }


    @GetMapping("/super/blog")
    public Result getSuperUserBlogs(@ModelAttribute BlogAdminFilterRequest filterRequest){
        return Result.SUCCESS(blogService.getUserAdminBlogs(-1,filterRequest,false));
    }

    @GetMapping("/super/delete_blog")
    public Result getSuperDeleteUserBlogs(@ModelAttribute BlogAdminFilterRequest filterRequest,HttpServletRequest request){
        int uid = ((User)request.getAttribute("user")).getId();
        return Result.SUCCESS(blogService.getUserAdminBlogs(uid,filterRequest,true));
    }

    @DeleteMapping("/blog/delete/{bid}")
    public Result deleteById(@PathVariable("bid") Integer bid,HttpServletRequest request){
        int uid = ((User)request.getAttribute("user")).getId();
        return Result.SUCCESS(blogService.deleteBlog(bid,true,uid));
    }

    @PutMapping("/blog/deletes")
    public Result deleteBlogs(@RequestBody List<Integer> bids, HttpServletRequest request){
        int uid = ((User)request.getAttribute("user")).getId();
        return Result.SUCCESS(blogService.deleteBlogs(bids,true,uid));
    }

    @PutMapping("/blog/un_deletes")
    public Result unDeleteBlogs(@RequestBody List<Integer> bids, HttpServletRequest request){
        int uid = ((User)request.getAttribute("user")).getId();
        return Result.SUCCESS(blogService.deleteBlogs(bids,false,uid));
    }

    @PutMapping("/blog/un_delete/{bid}")
    public Result unDeleteById(@PathVariable("bid") Integer bid,HttpServletRequest request){
        int uid = ((User)request.getAttribute("user")).getId();
        return Result.SUCCESS(blogService.deleteBlog(bid,false,uid));
    }

    @GetMapping("/category/list")
    public Result getCategoryList(@ModelAttribute CategoryFilterRequest request){
        return Result.SUCCESS(categoryService.getAdminCategoryList(request));
    }

    @PutMapping("/super/category/update")
    public Result updateCategory(@RequestBody CategoryVo categoryVo){
        return Result.SUCCESS(categoryService.updateCategory(categoryVo));
    }

    @DeleteMapping("/super/category/delete/{id}")
    public Result deleteCategory(@PathVariable("id") Integer id){
        return Result.SUCCESS(categoryService.deleteCategory(id,true));
    }

    @PutMapping("/super/category/un_delete/{id}")
    public Result unDeleteCategory(@PathVariable("id") Integer id){
        return Result.SUCCESS(categoryService.deleteCategory(id,false));
    }

    @PutMapping("/super/category/deletes")
    public Result deleteCategories(@RequestBody List<Integer> cids){
        System.out.println(cids);
        return Result.SUCCESS(categoryService.deleteCategories(cids,true));
    }

    @PutMapping("/super/category/un_deletes")
    public Result unDeleteCategories(@RequestBody List<Integer> cids){
        return Result.SUCCESS(categoryService.deleteCategories(cids,false));
    }

    @GetMapping("/tag/list")
    public Result getTagList(@ModelAttribute CategoryFilterRequest request){
        return Result.SUCCESS(tagService.getAdminCategoryList(request));
    }

    @PutMapping("/super/tag/update")
    public Result updateTag(@RequestBody TagVo tagVo){
        return Result.SUCCESS(tagService.updateTag(tagVo));
    }

    @DeleteMapping("/super/tag/delete/{id}")
    public Result deleteTag(@PathVariable("id") Integer id){
        return Result.SUCCESS(tagService.deleteTag(id,true));
    }

    @PutMapping("/super/tag/un_delete/{id}")
    public Result unDeleteTag(@PathVariable("id") Integer id){
        return Result.SUCCESS(tagService.deleteTag(id,false));
    }

    @PutMapping("/super/tag/deletes")
    public Result deleteTags(@RequestBody List<Integer> cids){
        System.out.println(cids);
        return Result.SUCCESS(tagService.deleteTags(cids,true));
    }

    @PutMapping("/super/tag/un_deletes")
    public Result unDeleteTags(@RequestBody List<Integer> cids){
        return Result.SUCCESS(tagService.deleteTags(cids,false));
    }
}
