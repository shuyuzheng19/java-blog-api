package com.zsy.service.impl;

import com.zsy.config.ManualJacksonSerializationService;
import com.zsy.constatns.RedisConstants;
import com.zsy.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * @author 郑书宇
 * @create 2023/8/31 12:00
 * @desc
 */
@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final RedisTemplate<String,String> redisTemplate;

    @Override
    public void saveUserEditBlog(int uid, String content) {
        redisTemplate.opsForHash().put(RedisConstants.USER_EDIT_BLOG_MAP, String.valueOf(uid), content);
    }

    @Override
    public String getSaveUserEditor(int uid) {
        Object result = redisTemplate.opsForHash().get(RedisConstants.USER_EDIT_BLOG_MAP, String.valueOf(uid));
        return Objects.isNull(result) ? "" : result.toString();
    }
}
