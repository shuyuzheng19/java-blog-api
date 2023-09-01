package com.zsy.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author 郑书宇
 * @create 2023/8/31 13:08
 * @desc
 */
@Component
@ConfigurationProperties(value = "upload")
@Data
public class UploadConfig {
    private String path;

    private String prefix;

    private String uri;

    private Long maxImageSize;

    private Long maxFileSize;

}
