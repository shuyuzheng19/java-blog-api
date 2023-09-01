package com.zsy.exception;

import com.zsy.model.response.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author 郑书宇
 * @create 2023/8/30 19:20
 * @desc
 */
@RestControllerAdvice
@Slf4j
public class ExceptionAdviceController {

    @ExceptionHandler(CustomException.class)
    public Result customExceptionError(CustomException customException){
        int code = customException.getCode();
        String message = customException.getMessage();
        log.warn("自定义错误,code:{}, message:{}",code,message);
        return Result.CUSTOMIZE(code,message);
    }

}
