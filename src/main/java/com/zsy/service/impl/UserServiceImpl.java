package com.zsy.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.zsy.config.ManualJacksonSerializationService;
import com.zsy.constatns.CustomConstants;
import com.zsy.constatns.RedisConstants;
import com.zsy.exception.CustomException;
import com.zsy.exception.ErrorEnum;
import com.zsy.mapper.UserMapper;
import com.zsy.model.pojo.Role;
import com.zsy.model.pojo.User;
import com.zsy.model.request.ContactRequest;
import com.zsy.model.request.LoginRequest;
import com.zsy.model.request.RegisteredRequest;
import com.zsy.model.response.AuthorInfo;
import com.zsy.model.response.Token;
import com.zsy.service.UserService;
import com.zsy.utils.JwtUtils;
import com.zsy.utils.OtherUtils;
import com.zsy.utils.ValidateUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

/**
 * @author 郑书宇
 * @create 2023/8/30 14:53
 * @desc
 */
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    @Resource
    private RedisTemplate<String,String> redisTemplate;

    @Resource
    private ManualJacksonSerializationService serializationService;

    @Resource
    private UserMapper userMapper;

    @Resource
    private ObjectMapper objectMapper;

    @Resource
    private JavaMailSender mailSender;

    @Resource
    private DefaultKaptcha kaptcha;

    @Value("${spring.mail.username}")
    private String myEmail;

    @Override
    public Optional<User> getUser(String username) {
        if (ObjectUtils.isEmpty(username)) throw new CustomException(ErrorEnum.ERROR_EMPTY);
        String KEY = RedisConstants.USER_INFO_KEY + username;
        String redisUser = redisTemplate.opsForValue().get(KEY);
        if (ObjectUtils.isEmpty(redisUser)) {
            Optional<User> user = userMapper.findByUsername(username);
            if (!user.isPresent()) return Optional.empty();
            redisTemplate.opsForValue().set(KEY,serializationService.serializeObject(user.get()), RedisConstants.USER_INFO_EXPIRE);
            return user;
        } else {
            return Optional.of(serializationService.deserializeObject(redisUser,User.class));
        }
    }

    @Override
    public void sendEmailGetCode(String toEmail) {
        if(ObjectUtils.isEmpty(toEmail) || !ValidateUtils.isValidEmail(toEmail)){
            throw new CustomException(ErrorEnum.ERROR_EMAIL_FORMAT);
        }
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject(CustomConstants.SEND_REGISTERED_EMAIL_SUBJECT);
        String randomCode = OtherUtils.createRandomCode();
        message.setText("你的验证码:(" + randomCode + ") 两分钟内有效");
        message.setFrom(myEmail);
        mailSender.send(message);
        redisTemplate.opsForValue().set(RedisConstants.EMAIL_CODE_KEY + toEmail, randomCode, RedisConstants.EMAIL_CODE_EMPIRE);
        log.info("发送验证码服务,对方邮箱:{}, 验证码:{}", toEmail, randomCode);
    }

    @Override
    public boolean validateEmailCode(String toEmail, String code) {
        Object targetCode = redisTemplate.opsForValue().get(RedisConstants.EMAIL_CODE_KEY + toEmail);
        if (ObjectUtils.isEmpty(targetCode)) return false;
        return code.equals(targetCode.toString());
    }

    @Override
    public Token Login(LoginRequest loginRequest,String ip) {
        loginRequest.check();
        Object code = redisTemplate.opsForValue().get(RedisConstants.IMAGE_CODE_KEY + ip);
        if(ObjectUtils.isEmpty(code)||!loginRequest.getCode().equals(code.toString())){
            throw new CustomException(ErrorEnum.ERROR_IMAGE_CODE_MISMATCH);
        }
        Optional<User> optionalUser = getUser(loginRequest.getUsername());
        if (!optionalUser.isPresent()) throw new CustomException(ErrorEnum.ERROR_USER_NOT_FOUND);
        User user = optionalUser.get();
        boolean flag = BCrypt.checkpw(loginRequest.getPassword(), user.getPassword());
        if (!flag) throw new CustomException(ErrorEnum.ERROR_PASSWORD_MISMATCH);
        String username = user.getUsername();
        Role role = user.getRole();
        String token = JwtUtils.generateAccessToken(username, role.getId());
        redisTemplate.opsForValue().set(RedisConstants.TOKEN_KEY + username, token, RedisConstants.TOKEN_EXPIRE);
        Token result = Token.builder().accessToken(token).username(username).role(role.getName()).ip(ip).build();
        log.info("用户:{}, 角色名称:{}, 登录成功",username,role.getName());
        return result;
    }

    @Override
    public byte[] generateBase64ImageCode(String ip) {
        String code = kaptcha.createText();

        BufferedImage image = kaptcha.createImage(code);

        ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();

        try {
            ImageIO.write(image,"png",byteArrayOutputStream);
        } catch (IOException e) {
            throw new CustomException(20000,"验证码生成出错");
        }

        redisTemplate.opsForValue().set(RedisConstants.IMAGE_CODE_KEY+ip,code,RedisConstants.IMAGE_CODE_EXPIRE);

//        String imageBase64 = Base64Utils.encodeToString(byteArrayOutputStream.toByteArray());

        byte[] imageByte =byteArrayOutputStream.toByteArray();

        try {
            byteArrayOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return imageByte;
    }

    @Override
    public void registeredUser(RegisteredRequest request) {
        request.check();
        boolean exists = userMapper.existsUsername(request.getUsername());
        if(exists) throw new CustomException(ErrorEnum.ERROR_USER_EXISTS);
        User user = request.toDo();
        int count = userMapper.saveUser(user);
        if(count==0) throw new CustomException(ErrorEnum.ERROR_REGISTERED_FAIL);
    }

    @Override
    public void sendEmailContactMe(ContactRequest contactRequest) {
        contactRequest.check();
        String html = String.format("<h3>%s</h3><p>对方名字:%s</p><p>对方邮箱:%s</p>留言内容:<p>%s</p>",
                contactRequest.getSubject(), contactRequest.getName(), contactRequest.getEmail(), contactRequest.getContent());
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(CustomConstants.MY_GMAIL_EMAIL);
        message.setSubject(CustomConstants.SEND_REGISTERED_EMAIL_SUBJECT);
        message.setText(html);
        message.setFrom(myEmail);
        log.info("联系我服务,主题:{},对方邮箱:{}",contactRequest.getSubject(),contactRequest.getEmail());
    }

    @Override
    public void logout(String username) {
        redisTemplate.delete(RedisConstants.TOKEN_KEY+username);
    }

    @Override
    public AuthorInfo getAuthorConfig() {
        String config = redisTemplate.opsForValue().get(RedisConstants.BLOG_CONFIG);
        if(Objects.isNull(config)) {
            return AuthorInfo.defaultAuthorInfo();
        }else{
            return serializationService.deserializeObject(config,AuthorInfo.class);
        }
    }

    @Override
    public void setAuthorInfo(AuthorInfo authorInfo) {
        redisTemplate.opsForValue().set(RedisConstants.BLOG_CONFIG,serializationService.serializeObject(authorInfo));
    }

    @Override
    public boolean validateToken(String username,String token) {
        Object redisToken = redisTemplate.opsForValue().get(RedisConstants.TOKEN_KEY + username);
        if(ObjectUtils.isEmpty(redisToken)){
            return false;
        }
        return token.equals(redisToken.toString());
    }
}
