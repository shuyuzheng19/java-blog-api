package com.zsy.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zsy.config.UploadConfig;
import com.zsy.constatns.CustomConstants;
import com.zsy.constatns.PageConstants;
import com.zsy.exception.CustomException;
import com.zsy.exception.ErrorEnum;
import com.zsy.mapper.FileMapper;
import com.zsy.model.pojo.FileInfo;
import com.zsy.model.pojo.FileMd5;
import com.zsy.model.response.PageResult;
import com.zsy.model.vo.BlogVo;
import com.zsy.model.vo.FileVo;
import com.zsy.service.FileService;
import com.zsy.utils.FileUtils;
import com.zsy.utils.ValidateUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import javax.annotation.Resource;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author 郑书宇
 * @create 2023/8/31 13:00
 * @desc
 */
@Service
public class FileServiceImpl implements FileService {

    @Resource
    private UploadConfig config;

    @Resource
    private FileMapper fileMapper;

    @Override
    @Transactional
    public List<String> uploadFile(boolean isPublic,MultipartFile[] files, String dir, int uid) throws IOException {
        List<String> urls = new ArrayList<>();

        for (MultipartFile file : files) {
            String fileName = file.getOriginalFilename();
            boolean flag = dir.equals(CustomConstants.FILES_DIR);
            long size = file.getSize();

            if (!flag) {
                if (!ValidateUtils.isImageFile(fileName)) {
                    throw new CustomException(ErrorEnum.ERROR_NOT_IMAGE_FILE);
                }
                if (size > config.getMaxImageSize()) {
                    throw new CustomException(ErrorEnum.ERROR_IMAGE_FILE_SIZE);
                }
            } else {
                if (size > config.getMaxFileSize()) {
                    throw new CustomException(ErrorEnum.ERROR_FILE_FILE_SIZE);
                }
            }

            byte[] fileBytes = file.getBytes();
            String md5 = FileUtils.getFileMd5(fileBytes);
            String md5Url = fileMapper.findMd5(md5);
            boolean exists = md5Url != null && !md5Url.isEmpty();
            boolean flag2 = dir.equals(CustomConstants.AVATAR_DIR);

            if (exists && flag2) {
                urls.add(md5Url);
                continue;
            }

            String suffix = FileUtils.getFileExtension(fileName);
            String newFileName = java.util.UUID.randomUUID() + "."+ suffix;
            String filePath = config.getPath() + "/" + dir + "/" + newFileName;
            String url;

            if (!exists) {
                file.transferTo(Paths.get(filePath));
                url = config.getUri() + dir + "/" + newFileName;
                int i = fileMapper.saveFileMd5(FileMd5.of(md5, url));
                if(i==0){
                    continue;
                }
            } else {
                url = md5Url;
            }

            if (flag) {
                FileInfo fileInfo = new FileInfo();
                fileInfo.setUserId(uid);
                fileInfo.setOldName(fileName);
                fileInfo.setNewName(newFileName);
                fileInfo.setDate(new Date());
                fileInfo.setSize(size);
                fileInfo.setSuffix(suffix);
                fileInfo.setAbsolutePath(filePath);
                fileInfo.setPublic(isPublic);
                fileInfo.setFileMd5Id(md5);
                fileMapper.saveFileInfo(fileInfo);
            }
            urls.add(url);
        }
        return urls;
    }

    @Override
    public PageResult getFileInfos(int page,int uid, String keyword, String sort) {
        if(!sort.equals("size")) {
            sort = "create_at";
        }
        PageHelper.startPage(page, PageConstants.FILE_LIST_COUNT);
        fileMapper.getFileInfos(uid, keyword, sort);
        PageInfo<FileVo> pageInfo = PageInfo.of(fileMapper.getFileInfos(uid,keyword,sort));
        return PageResult.builder().page(pageInfo.getPageNum()).size(pageInfo.getPageSize()).total(pageInfo.getTotal()).data(pageInfo.getList()).build();
    }
}
