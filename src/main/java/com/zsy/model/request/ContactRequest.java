package com.zsy.model.request;

import com.zsy.exception.CustomException;
import com.zsy.utils.ValidateUtils;
import lombok.Data;

@Data
public class ContactRequest {
    private String name;
    private String email;
    private String subject;
    private String content;

    public void check() throws IllegalArgumentException {
        if (email == null || !ValidateUtils.isValidEmail(email)) {
            throw new CustomException(10000,"错误的邮箱格式");
        } else if (name == null) {
            throw new CustomException(10000,"请输入你的名称");
        } else if (subject == null) {
            throw new CustomException(10000,"请输入主题内容");
        } else if (content == null) {
            throw new CustomException(10000,"请输入消息内容");
        }
    }
}
