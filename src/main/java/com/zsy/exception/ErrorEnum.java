package com.zsy.exception;

/**
 * @author 郑书宇
 * @create 2023/8/30 15:00
 * @desc
 */
public enum ErrorEnum {
    ERROR_EMPTY(10000,"参数错误或为空"),
    ERROR_USER_NOT_FOUND(10001,"该用户不存在"),
    ERROR_PASSWORD_MISMATCH(10002,"密码错误"),
    ERROR_IMAGE_CODE_MISMATCH(10003,"验证码错误"),
    ERROR_USER_EXISTS(10004,"该用户已存在,换个账号吧"),
    ERROR_REGISTERED_FAIL(10005,"注册失败了"),
    ERROR_EMAIL_FORMAT(10006,"错误的邮箱格式"),
    ERROR_CHAT_GPT_FAIL(10007,"请求ChatGpt出错"),
    ERROR_SAVE_BLOG_FAIL(10008,"添加博客失败"),
    ERROR_NOT_IMAGE_FILE(10009,"这不是一个图片文件"),
    ERROR_IMAGE_FILE_SIZE(10010,"图片文件大小超出"),
    ERROR_FILE_FILE_SIZE(10011,"文件大小超出"),
    ERROR_SAVE_TOPIC_FAIL(10012,"添加专题失败"),
    ERROR_SAVE_CATEGORY_FAIL(10013,"添加分类失败"),
    ERROR_SAVE_TAG_FAIL(10014,"添加标签失败"),
    ERROR_NOT_FOUND_BLOG(10015,"该博客不存在")
    ;


    private int code;

    private String message;

    ErrorEnum(int code,String message){
        this.code=code;
        this.message=message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
