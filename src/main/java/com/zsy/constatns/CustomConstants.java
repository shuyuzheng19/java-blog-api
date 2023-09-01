package com.zsy.constatns;

/**
 * @author 郑书宇
 * @create 2023/8/30 15:08
 * @desc
 */
public interface CustomConstants {
    //发送邮件显示的主题
    String SEND_REGISTERED_EMAIL_SUBJECT="Yuice博客验证码";
    //联系我，发送到我的gmail邮箱
    String MY_GMAIL_EMAIL="shuyuzheng19@gmail.com";
    //全局API前缀
    String API_PREFIX="/api/v1";
    //普通用户角色名
    String USER_ROLE="USER";
    //管理员角色名
    String ADMIN_ROLE="ADMIN";
    //超级管理员角色名
    String SUPER_ADMIN_ROLE="SUPER_ADMIN";
    //博客搜索引擎的INDEX
    String SEARCH_BLOG_INDEX="articles";
    //文件目录名称
    String AVATAR_DIR="avatar";
    String IMAGES_DIR="images";
    String FILES_DIR="files";
}
