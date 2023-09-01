package com.zsy.model.request;

import com.zsy.exception.CustomException;
import lombok.Data;
import org.springframework.util.ObjectUtils;

/**
 * @author 郑书宇
 * @create 2023/8/30 15:53
 * @desc
 */

@Data
public class LoginRequest {
    private String username;
    private String password;
    private String code;

    public void check(){
        if(ObjectUtils.isEmpty(username)) {
            throw new CustomException(10000, "账号不能为空");
        }else if(ObjectUtils.isEmpty(password)){
            throw new CustomException(10000,"密码不能为空");
        }else if(ObjectUtils.isEmpty(code)){
            throw new CustomException(10000,"验证码不能为空");
        }
    }

    public static void main(String[] args) {
        LoginRequest loginRequest=new LoginRequest();
        System.out.println(ObjectUtils.isEmpty(loginRequest));
    }
}
