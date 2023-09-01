package com.zsy.interceptor;

import com.zsy.config.ManualJacksonSerializationService;
import com.zsy.constatns.CustomConstants;
import com.zsy.model.pojo.User;
import com.zsy.model.response.Result;
import com.zsy.service.UserService;
import com.zsy.utils.JwtUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.util.ObjectUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

/**
 * @author 郑书宇
 * @create 2023/8/30 18:04
 * @desc
 */
public class SecurityInterceptor implements HandlerInterceptor{

    private final String TOKEN_PREFIX="Bearer ";

    private final UserService userService;

    private final ManualJacksonSerializationService manualJacksonSerializationService;

    public SecurityInterceptor(UserService userService, ManualJacksonSerializationService manualJacksonSerializationService) {
        this.userService = userService;
        this.manualJacksonSerializationService = manualJacksonSerializationService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String tokenHeader=request.getHeader(HttpHeaders.AUTHORIZATION);

        if(ObjectUtils.isEmpty(tokenHeader) || !tokenHeader.startsWith(TOKEN_PREFIX)){
            manualJacksonSerializationService.writeJsonToResponse(Result.CUSTOMIZE(401,"你还未登录"),response);
            return false;
        }

        String token=tokenHeader.replace(TOKEN_PREFIX,"");

        String username = JwtUtils.verifyTokenAndGetUsername(token);

        if(ObjectUtils.isEmpty(username)){
            manualJacksonSerializationService.writeJsonToResponse(Result.CUSTOMIZE(403,"可能token已过期,请重新登录"),response);
            return false;
        }

        Optional<User> user = userService.getUser(username);

        if(!user.isPresent()){
            manualJacksonSerializationService.writeJsonToResponse(Result.CUSTOMIZE(403,"该用户不存在"),response);
            return false;
        }

        if(userService.validateToken(username,token)) {
            User currentUser = user.get();
            String role = currentUser.getRole().getName();
            request.setAttribute("user",currentUser);
            if(role.equals(CustomConstants.SUPER_ADMIN_ROLE)){
                return true;
            }
            String path = request.getServletPath();
            if(path.contains("/auth/")) {
                return true;
            }else if(!path.startsWith(CustomConstants.API_PREFIX+"/admin/super")&&role.equals(CustomConstants.ADMIN_ROLE)) {
                return true;
            }
        }
        manualJacksonSerializationService.writeJsonToResponse(Result.CUSTOMIZE(403,"认证失败"),response);
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    }
}
