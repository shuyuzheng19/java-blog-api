package com.zsy.config;

import com.zsy.constatns.CustomConstants;
import com.zsy.interceptor.SecurityInterceptor;
import com.zsy.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;
import java.util.Arrays;

/**
 * @author 郑书宇
 * @create 2023/8/30 19:05
 * @desc
 */
@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {

    @Resource
    private UserService userService;

    @Resource
    private ManualJacksonSerializationService manualJacksonSerializationService;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new SecurityInterceptor(userService, manualJacksonSerializationService)).addPathPatterns(Arrays.asList(
                CustomConstants.API_PREFIX+"/*/auth/**",
                CustomConstants.API_PREFIX+"/*/admin/**",
                CustomConstants.API_PREFIX+"/admin/**"
        ));
    }

    @Bean
    public RestTemplate restTemplate(){
        RestTemplate restTemplate = new RestTemplate();
        SimpleClientHttpRequestFactory rf = (SimpleClientHttpRequestFactory) restTemplate.getRequestFactory();
        rf.setConnectTimeout(5000);  // 连接超时
        rf.setReadTimeout(5000);     // 读取
        restTemplate.setRequestFactory(rf);
        return restTemplate;
    }

}

