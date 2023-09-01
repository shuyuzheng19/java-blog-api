package com.zsy.model.request;

import com.zsy.exception.CustomException;
import com.zsy.model.pojo.User;
import com.zsy.utils.ValidateUtils;
import lombok.Data;

import java.util.Date;

/**
 * @author 郑书宇
 * @create 2023/8/30 16:52
 * @desc
 */
@Data
public class RegisteredRequest {
    private String username; // 用户名
    private String nickName; // 昵称
    private String password; // 密码
    private String email;    // 邮箱
    private String icon;     // 头像链接
    private String code;     // 邮箱验证码

    public void check() throws IllegalArgumentException {
        if (username == null || username.length() < 8 || username.length() > 16) {
            throw new CustomException(10000,"用户名要在8-16个字符之间");
        } else if (nickName == null || (nickName.length() < 1 || nickName.length() > 20)) {
            throw new CustomException(10000,"用户名要在1-20个字符之间");
        } else if (password == null || password.length() < 8 || password.length() > 16) {
            throw new CustomException(10000,"密码要在8-16个字符之间");
        } else if (email == null || !ValidateUtils.isValidEmail(email)) {
            throw new CustomException(10000,"邮箱格式不正确");
        } else if (icon == null || !ValidateUtils.isImageUrl(icon)) {
            throw new CustomException(10000,"这不是一个有效的图片链接");
        }
    }

    public User toDo(){
        Date now = new Date();
        User user = new User();
        user.setUsername(username);
        user.setNickName(nickName);
        user.setPassword(password);
        user.setEmail(email);
        user.setIcon(icon);
        user.setCreateAt(now);
        user.setUpdateAt(now);
        user.setRoleId(1);
        return user;
    }
}
