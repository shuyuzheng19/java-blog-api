package com.zsy.model.pojo;

import lombok.Data;

import java.util.Date;

@Data
public class FileInfo {
    private Integer id;
    private Integer userId;
    private String oldName;
    private String newName;
    private Date date;
    private Long size;
    private String suffix;
    private String absolutePath;
    private boolean isPublic;
    private Date deletedAt;
    private String fileMd5Id;
    private FileMd5 fileMd5;

}
