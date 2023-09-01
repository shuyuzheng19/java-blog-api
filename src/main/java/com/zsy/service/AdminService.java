package com.zsy.service;

/**
 * @author 郑书宇
 * @create 2023/8/31 12:00
 * @desc
 */
public interface AdminService {
    void saveUserEditBlog(int uid,String content);
    String getSaveUserEditor(int uid);
}
