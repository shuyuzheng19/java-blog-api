package com.zsy.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

/**
 * @author 郑书宇
 * @create 2023/8/30 17:42
 * @desc
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class Result {
    //状态码
    private int code;

    //返回消息
    private String message;

    //返回的数据
    private Object data;

    //成功
    public static final Result OK = Result.builder().code(200).message("成功").build();
    //失败
    public static final Result FAIL=Result.builder().code(30000).message("失败").build();
    //错误
    public static final Result ERROR=Result.builder().code(500).message("错误").build();

    public static Result SUCCESS(Object data){
        return Result.builder().code(200).message("成功").data(data).build();
    }

    public static Result CUSTOMIZE(int code,String message){
        return Result.builder().code(code).message(message).build();
    }
}
