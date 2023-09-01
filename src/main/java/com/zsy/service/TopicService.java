package com.zsy.service;

import com.zsy.model.request.TopicRequest;
import com.zsy.model.response.PageResult;
import com.zsy.model.vo.SimpleBlogVo;
import com.zsy.model.vo.SimpleTopicVo;
import com.zsy.model.vo.TopicVo;

import java.util.List;

/**
 * @author 郑书宇
 * @create 2023/8/31 2:38
 * @desc
 */
public interface TopicService {
    List<SimpleTopicVo> findAllTopic();
    PageResult findPageTopic(int page);
    PageResult getTopicBlogList(int page,Integer tid);
    SimpleTopicVo getTopicById(Integer tid);
    List<SimpleTopicVo> getUserTopicList(Integer uid);
    List<SimpleBlogVo> getTopicAllBlog(Integer tid);
    SimpleTopicVo addTopic(TopicRequest topicRequest,Integer uid);
}
