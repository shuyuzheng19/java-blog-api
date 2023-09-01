package com.zsy.constatns;

import java.time.Duration;

/**
 * @author 郑书宇
 * @create 2023/8/30 15:34
 * @desc
 */
public interface RedisConstants {
    //邮箱验证码以及过期时间
    String EMAIL_CODE_KEY  = "EMAIL-CODE:";
    Duration EMAIL_CODE_EMPIRE= Duration.ofMinutes(2);
    //用户信息以及过期时间
    String USER_INFO_KEY = "USER-INFO:";
    Duration USER_INFO_EXPIRE=Duration.ofMinutes(30);
    //TOKEN以及过期时间
    String TOKEN_KEY = "USER-TOKEN:";
    Duration TOKEN_EXPIRE= Duration.ofDays(7);
    //图形验证码以及过期时间
    String IMAGE_CODE_KEY="LOGIN-IMAGE-CODE:";
    Duration IMAGE_CODE_EXPIRE=Duration.ofMinutes(1);
    //获取作者相关信息
    String BLOG_CONFIG = "BLOG-CONFIG";
    //热门博客以及过期时间
    String HOT_BLOG="HOT-BLOG";
    Duration HOT_BLOG_EXPIRE = Duration.ofMinutes(30);
    //随机博客
    String RANDOM_BLOG="RANDOM-BLOG";
    Duration RANDOM_BLOG_EXPIRE = Duration.ofDays(7);
    //推荐博客
    String RECOMMEND_BLOG_KEY="RECOMMEND-BLOG";
    //博客详情MAP
    String BLOG_MAP = "BLOG-MAP";
    //分类列表
    String CATEGORY_LIST="CATEGORY-LIST";
    Duration CATEGORY_LIST_EXPIRE = Duration.ofDays(1);
    //标签云
    String RANDOM_TAG_LIST="RANDOM-TAG";
    Duration RANDOM_TAG_LIST_EXPIRE = Duration.ofDays(1);
    //第一页专题
    String FIRST_PAGE_TOPIC="FIRST-TOPIC-PAGE";
    Duration FIRST_PAGE_TOPIC_EXPIRE=Duration.ofHours(2);
    //保存用户编辑的文章
    String USER_EDIT_BLOG_MAP="USER-EDITOR-SAVE-MAP";
    //存储博客点击量
    String BLOG_EYE_COUNT_MAP_KEY = "BLOG-EYE-COUNT-MAP";
}
