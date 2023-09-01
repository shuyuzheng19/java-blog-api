package com.zsy.model.pojo;

import lombok.Data;

@Data
public class FileMd5{
    private String md5;
    private String url;

    public static FileMd5 of(String md5,String url){
        FileMd5 fileMd5=new FileMd5();
        fileMd5.setMd5(md5);
        fileMd5.setUrl(url);
        return fileMd5;
    }
}
