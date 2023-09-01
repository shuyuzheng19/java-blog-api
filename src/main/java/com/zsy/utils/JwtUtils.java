package com.zsy.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.zsy.constatns.RedisConstants;

import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;

/**
 * @author 郑书宇
 * @create 2023/8/30 16:19
 * @desc
 */
public class JwtUtils {

    //加密规则
    private static final Algorithm DEFAULT_ALGORITHM= Algorithm.HMAC256("xiaoyu!@@#!!@#@");

    //生成token
    public static String generateAccessToken(String username,Integer roleId){
        return JWT.create()
                .withClaim("username",username)
                .withClaim("role",roleId)
                .withKeyId(UUID.randomUUID().toString())
                .withExpiresAt(new Date(System.currentTimeMillis()+ RedisConstants.TOKEN_EXPIRE.toMillis()))
                .withSubject(username)
                .withJWTId(UUID.randomUUID().toString())
                .sign(DEFAULT_ALGORITHM);
    }

    //验证token并返回该用户名 如果验证失败则返回null
    public static String verifyTokenAndGetUsername(String token){
        JWTVerifier verifier = JWT.require(DEFAULT_ALGORITHM).build();
        try{
            DecodedJWT verify = verifier.verify(token);
            String username = verify.getSubject();
            return username;
        }catch (Exception e){
            return null;
        }
    }
}
