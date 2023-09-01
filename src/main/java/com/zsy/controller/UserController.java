package com.zsy.controller;

import com.zsy.constatns.CustomConstants;
import com.zsy.model.pojo.User;
import com.zsy.model.request.LoginRequest;
import com.zsy.model.request.RegisteredRequest;
import com.zsy.model.response.Result;
import com.zsy.model.response.Token;
import com.zsy.service.ChatGptService;
import com.zsy.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

/**
 * @author 郑书宇
 * @create 2023/8/30 17:38
 * @desc
 */
@RequestMapping(CustomConstants.API_PREFIX+"/user")
@RestController
@Slf4j
public class UserController {

    @Resource
    private UserService userService;

    @PostMapping("/registered")
    public Result registeredUser(@RequestBody RegisteredRequest registeredRequest){
        userService.registeredUser(registeredRequest);
        return Result.OK;
    }

    @GetMapping("/send_mail")
    public Result sendEmail(String email){
        userService.sendEmailGetCode(email);
        return Result.OK;
    }

    @GetMapping("/captcha")
    public void createCaptcha(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String ip = request.getRemoteAddr();
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
        response.addHeader("Cache-Control", "post-check=0, pre-check=0");
        response.setHeader("Pragma", "no-cache");
        response.setContentType("image/jpeg");
        try (OutputStream outputStream = response.getOutputStream()) {
            byte[] captchaChallengeAsJpeg = userService.generateBase64ImageCode(ip);
            outputStream.write(captchaChallengeAsJpeg);
            outputStream.flush();
        }
    }

    @PostMapping("/login")
    public Result login(@RequestBody LoginRequest loginRequest,HttpServletRequest request){
        Token token = userService.Login(loginRequest, request.getRemoteAddr());
        return Result.SUCCESS(token);
    }

    @GetMapping("/auth/logout")
    public Result logout(HttpServletRequest request){
        String username = ((User)request.getAttribute("user")).getUsername();
        userService.logout(username);
        return Result.OK;
    }

    @GetMapping("/config")
    public Result authorConfig(){
        return Result.SUCCESS(userService.getAuthorConfig());
    }

    @GetMapping("/auth/get")
    public Result getCurrentUser(HttpServletRequest request){
        User user = (User)request.getAttribute("user");
        return Result.SUCCESS(user.toVo());
    }

    @Resource
    private ChatGptService chatGptService;

    @GetMapping("/chat")
    public SseEmitter chatGptHelper(String message) throws IOException {
        return chatGptService.chat(message);
    }

}
