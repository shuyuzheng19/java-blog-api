package com.zsy.model.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.zsy.utils.FileUtils;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 郑书宇
 * @create 2023/8/31 14:13
 * @desc
 */
@Data
public class FileVo implements Serializable {
    private Integer id;
    @JsonProperty("name")
    private String oldName;
    @JsonProperty("dateStr")
    private Date createAt;
    private Long size;
    private String suffix;
    private String sizeStr;
    private String md5;
    private String url;

    public String getSizeStr() {
        return FileUtils.getSizeStr(size);
    }
}
