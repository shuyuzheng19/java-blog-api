package com.zsy.exception;

import lombok.Data;

/**
 * @author 郑书宇
 * @create 2023/8/30 14:55
 * @desc
 */
@Data
public class CustomException extends RuntimeException{

    //错误代码
    private int code;

    //错误消息
    private String message;

    public CustomException(int code,String message){
        this.code=code;
        this.message=message;
    }

    public CustomException(ErrorEnum errorEnum){
        this.code=errorEnum.getCode();
        this.message=errorEnum.getMessage();
    }
}
