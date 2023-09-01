package com.zsy.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author 郑书宇
 * @create 2023/8/30 15:10
 * @desc
 */
public class OtherUtils {

    //随机生成6位数字验证码
    public static String createRandomCode(){
        int code = (int)( Math.random()*900000) + 100000;
        return String.valueOf(code);
    }

}
