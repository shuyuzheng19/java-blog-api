package com.zsy.service;

import com.zsy.model.response.PageResult;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * @author 郑书宇
 * @create 2023/8/31 13:00
 * @desc
 */
public interface FileService {
    List<String> uploadFile(boolean isPublic,MultipartFile[] files,String dir,int uid) throws IOException;
    PageResult getFileInfos(int page,int uid,String keyword,String sort);
}
