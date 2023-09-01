package com.zsy.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

/**
 * @author 郑书宇
 * @create 2023/8/30 15:50
 * @desc
 */
@Data @Builder
public class Token {
    /**
     * 用户名
     */
    @JsonProperty("username")
    private String username;

    /**
     * 访问令牌
     */
    @JsonProperty("access_token")
    private String accessToken;

    /**
     * 角色
     */
    @JsonProperty("role")
    private String role;

    /**
     * IP
     */
    @JsonProperty("ip")
    private String ip;
}
