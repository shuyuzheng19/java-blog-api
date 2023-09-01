package com.zsy.service;

import com.zsy.model.pojo.User;
import com.zsy.model.request.ContactRequest;
import com.zsy.model.request.LoginRequest;
import com.zsy.model.request.RegisteredRequest;
import com.zsy.model.response.AuthorInfo;
import com.zsy.model.response.Token;

import java.util.Optional;

/**
 * 此服务接口提供与用户操作相关的方法。
 *
 * @author 郑书宇
 * @create 2023/8/30 14:28
 */
public interface UserService {

    /**
     * 根据提供的用户名获取用户。
     *
     * @param username 要获取的用户的用户名。
     * @return 包含用户的 Optional，如果未找到则为一个空的 Optional。
     */
    Optional<User> getUser(String username);

    /**
     * 发送电子邮件到用户的邮箱
     *
     * @param toEmail 收件人电子邮件地址。
     */
    void sendEmailGetCode(String toEmail);

    /**
     * 验证电子邮件验证码。
     *
     * @param toEmail 收件人电子邮件地址。
     * @param code 要验证的验证码。
     * @return 如果验证码有效则为 true，否则为 false。
     */
    boolean validateEmailCode(String toEmail, String code);

    /**
     * 用户登录，返回令牌。
     *
     * @param loginRequest 登录请求信息。
     * @param ip 用户登录的 IP 地址。
     * @return 登录成功后的令牌。
     */
    Token Login(LoginRequest loginRequest, String ip);

    /**
     * 生成指定 IP 地址的 Base64 登录图片验证码。
     *
     * @param ip 用户 IP 地址。
     * @return 生成的 Base64 登录图片验证码。
     */
    byte[] generateBase64ImageCode(String ip);

    /**
     * 注册用户。
     *
     * @param request 注册请求信息。
     */
    void registeredUser(RegisteredRequest request);

    /**
     * 发送电子邮件以联系我。
     *
     * @param contactRequest 联系请求信息。
     */
    void sendEmailContactMe(ContactRequest contactRequest);

    /**
     * 注销用户。
     *
     * @param username 用户名。
     */
    void logout(String username);

    /**
     * 获取作者配置信息。
     *
     * @return 作者配置信息。
     */
    AuthorInfo getAuthorConfig();

    /**
     * 设置作者配置信息。
     *
     * @param authorInfo 要设置的作者配置信息。
     */
    void setAuthorInfo(AuthorInfo authorInfo);

    /**
     * 验证用户token
     */
    boolean validateToken(String username,String token);
}
