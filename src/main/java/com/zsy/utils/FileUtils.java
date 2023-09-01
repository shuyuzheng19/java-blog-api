package com.zsy.utils;

import org.springframework.util.DigestUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.io.InputStream;
import java.text.DecimalFormat;

/**
 * @author 郑书宇
 * @create 2023/8/31 13:15
 * @desc
 */
public class FileUtils {

    //获取文件的md5
    public static String getFileMd5(byte[] bytes){
        return DigestUtils.md5DigestAsHex(bytes);
    }

    //获取文件后缀
    public static String getFileExtension(String path) {
        if(ObjectUtils.isEmpty(path)) {
            return "";
        }else{
            return path.substring(path.lastIndexOf(".")+1);
        }
    }

    //获取文件大小
    public static String getSizeStr(double size) {
        if (size == 0) {
            return "0 B";
        }

        String[] units = {"B", "KB", "MB", "GB", "TB"};
        int unitIndex = 0;
        while (size >= 1024 && unitIndex < units.length - 1) {
            size /= 1024;
            unitIndex++;
        }

        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        return decimalFormat.format(size) + " " + units[unitIndex];
    }


}
