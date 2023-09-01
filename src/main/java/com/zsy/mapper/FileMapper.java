package com.zsy.mapper;

import com.zsy.model.pojo.FileInfo;
import com.zsy.model.pojo.FileMd5;
import com.zsy.model.vo.FileVo;

import java.util.List;

/**
 * @author 郑书宇
 * @create 2023/8/31 13:00
 * @desc
 */
public interface FileMapper {
    String findMd5(String md5);

    int saveFileInfo(FileInfo fileInfo);

    int saveFileMd5(FileMd5 fileMd5);

    List<FileVo> getFileInfos(int uid,String keyword,String sort);
}
