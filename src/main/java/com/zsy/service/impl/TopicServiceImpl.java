package com.zsy.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zsy.config.ManualJacksonSerializationService;
import com.zsy.constatns.PageConstants;
import com.zsy.constatns.RedisConstants;
import com.zsy.exception.CustomException;
import com.zsy.exception.ErrorEnum;
import com.zsy.mapper.TopicMapper;
import com.zsy.model.pojo.Topic;
import com.zsy.model.request.TopicRequest;
import com.zsy.model.response.PageResult;
import com.zsy.model.vo.BlogVo;
import com.zsy.model.vo.SimpleBlogVo;
import com.zsy.model.vo.SimpleTopicVo;
import com.zsy.model.vo.TopicVo;
import com.zsy.service.TopicService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

/**
 * @author 郑书宇
 * @create 2023/8/31 2:39
 * @desc
 */
@Service
@RequiredArgsConstructor
public class TopicServiceImpl implements TopicService {

    private final TopicMapper topicMapper;

    private final RedisTemplate<String,String> redisTemplate;

    private final ManualJacksonSerializationService objectSerializationService;


    @Override
    public List<SimpleTopicVo> findAllTopic() {
        return topicMapper.findAllTopic();
    }

    @Override
    public PageResult findPageTopic(int page) {
        if(page==1){
            String result = redisTemplate.opsForValue().get(RedisConstants.FIRST_PAGE_TOPIC);
            if(Objects.isNull(result)) {
                PageHelper.startPage(page, PageConstants.TOPIC_LIST_COUNT);
                PageInfo<TopicVo> pageInfo = PageInfo.of(topicMapper.findPageTopicList());
                PageResult pageResult = PageResult.builder().page(pageInfo.getPageNum()).size(pageInfo.getPageSize()).total(pageInfo.getTotal()).data(pageInfo.getList()).build();
                redisTemplate.opsForValue().set(RedisConstants.FIRST_PAGE_TOPIC,objectSerializationService.serializeObject(pageResult),RedisConstants.FIRST_PAGE_TOPIC_EXPIRE);
                return pageResult;
            }else{
                return objectSerializationService.deserializeObject(result,PageResult.class);
            }
        }
        PageHelper.startPage(page, PageConstants.TOPIC_LIST_COUNT);
        PageInfo<TopicVo> pageInfo = PageInfo.of(topicMapper.findPageTopicList());
        return PageResult.builder().page(pageInfo.getPageNum()).size(pageInfo.getPageSize()).total(pageInfo.getTotal()).data(pageInfo.getList()).build();
    }

    @Override
    public PageResult getTopicBlogList(int page,Integer tid) {
        if(tid==null||tid<=0) throw new CustomException(ErrorEnum.ERROR_EMPTY);
        PageHelper.startPage(page, PageConstants.BLOG_LIST_PAGE_COUNT);
        PageInfo<BlogVo> pageInfo = PageInfo.of(topicMapper.findTopicIdBlogList(tid));
        return PageResult.builder().page(pageInfo.getPageNum()).size(pageInfo.getPageSize()).total(pageInfo.getTotal()).data(pageInfo.getList()).build();
    }

    @Override
    public SimpleTopicVo getTopicById(Integer tid) {
        if(tid==null||tid<=0) throw new CustomException(ErrorEnum.ERROR_EMPTY);
        return topicMapper.findTopicById(tid);
    }

    @Override
    public List<SimpleTopicVo> getUserTopicList(Integer uid) {
        return topicMapper.findUserTopicList(uid);
    }

    @Override
    public List<SimpleBlogVo> getTopicAllBlog(Integer tid) {
        if(tid==null||tid<=0) throw new CustomException(ErrorEnum.ERROR_EMPTY);
        return topicMapper.getTopicBlogList(tid);
    }

    @Override
    @Transactional
    public SimpleTopicVo addTopic(TopicRequest topicRequest,Integer uid) {
        topicRequest.check();
        Topic topic=topicRequest.toDo(uid);
        int count = topicMapper.addTopic(topic);
        if(count==0){
            throw new CustomException(ErrorEnum.ERROR_SAVE_TOPIC_FAIL);
        }
        redisTemplate.delete(RedisConstants.FIRST_PAGE_TOPIC);
        SimpleTopicVo topicVo=new SimpleTopicVo();
        topicVo.setId(topic.getId());
        topicVo.setName(topic.getName());
        return topicVo;
    }
}
