package com.zsy.utils;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author 郑书宇
 * @create 2023/8/30 14:46
 * @desc 验证相关的工具类
 */
public class ValidateUtils {
    //判断邮箱正则
    private static final String EMAIL_PATTERN = "^[A-Za-z0-9+_.-]+@(.+)$";
    //判断图片URL正则
    private static final String imageRegex = "^(http|https)://.*\\.(jpg|jpeg|png|gif)$";

    //判断是否为邮箱格式
    public static boolean isValidEmail(String email) {
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    //判断是否为图片URL
    public static boolean isImageUrl(String url) {
        Pattern pattern = Pattern.compile(imageRegex);
        Matcher matcher = pattern.matcher(url);
        return matcher.matches();
    }

    //判断是否为图片文件
    public static boolean isImageFile(String filename) {
        // 将文件名转换为小写字母，并获取文件扩展名
        String ext = filename.toLowerCase().substring(filename.lastIndexOf("."));

        // 检查文件扩展名是否为图片格式
        List<String> allowedExtensions = Arrays.asList(".jpg", ".jpeg", ".png", ".gif");
        return allowedExtensions.contains(ext);
    }


}
