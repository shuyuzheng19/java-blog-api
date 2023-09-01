package com.zsy.mapper;

import com.zsy.model.pojo.Topic;
import com.zsy.model.vo.BlogVo;
import com.zsy.model.vo.SimpleBlogVo;
import com.zsy.model.vo.SimpleTopicVo;
import com.zsy.model.vo.TopicVo;

import java.util.List;

/**
 * @author 郑书宇
 * @create 2023/8/31 2:32
 * @desc
 */
public interface TopicMapper {
    List<SimpleTopicVo> findAllTopic();
    List<TopicVo> findPageTopicList();
    List<BlogVo> findTopicIdBlogList(Integer tid);
    SimpleTopicVo findTopicById(Integer tid);
    List<SimpleTopicVo> findUserTopicList(Integer uid);
    List<SimpleBlogVo> getTopicBlogList(Integer tid);
    int addTopic(Topic topic);
}
