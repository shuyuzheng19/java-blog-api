package com.zsy.controller;

import com.zsy.constatns.CustomConstants;
import com.zsy.model.pojo.User;
import com.zsy.model.response.PageResult;
import com.zsy.model.response.Result;
import com.zsy.service.FileService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

/**
 * @author 郑书宇
 * @create 2023/8/31 13:50
 * @desc
 */
@RequestMapping(CustomConstants.API_PREFIX+"/file")
@RestController
public class FileController {

    @Resource
    private FileService fileService;

    @GetMapping("/public")
    public Result publicFiles(@RequestParam(defaultValue = "create") String sort, String keyword,@RequestParam(defaultValue = "1") Integer page){
        PageResult fileInfos = fileService.getFileInfos(page, -1, keyword, sort);
        return Result.SUCCESS(fileInfos);
    }

    @GetMapping("/admin/current")
    public Result getCurrentFiles(@RequestParam(defaultValue = "create") String sort,
                                  String keyword,
                                  @RequestParam(defaultValue = "1") Integer page,
                                  HttpServletRequest request
                                  ){
        int uid = ((User)request.getAttribute("user")).getId();
        PageResult fileInfos = fileService.getFileInfos(page, uid, keyword, sort);
        return Result.SUCCESS(fileInfos);
    }

    @PostMapping("/upload/avatar")
    public Result uploadAvatar(MultipartFile[] files) throws IOException {
        List<String> urls = fileService.uploadFile(false,files,CustomConstants.AVATAR_DIR,-1);
        return Result.SUCCESS(urls);
    }

    @PostMapping("/auth/upload/image")
    public Result uploadImages(MultipartFile[] files, boolean isPublic, HttpServletRequest request) throws IOException {
        int uid = ((User)request.getAttribute("user")).getId();
        List<String> urls = fileService.uploadFile(isPublic,files,CustomConstants.IMAGES_DIR,uid);
        return Result.SUCCESS(urls);
    }

    @PostMapping("/admin/upload/file")
    public Result uploadFiles(MultipartFile[] files,boolean isPublic, HttpServletRequest request) throws IOException {
        int uid = ((User)request.getAttribute("user")).getId();
        List<String> urls = fileService.uploadFile(isPublic,files,CustomConstants.FILES_DIR,uid);
        return Result.SUCCESS(urls);
    }

}
