package com.zsy.model.request;

import com.zsy.exception.CustomException;
import com.zsy.model.pojo.Topic;
import com.zsy.utils.ValidateUtils;
import lombok.Data;
import org.springframework.util.ObjectUtils;

import java.util.Date;

/**
 * @author 郑书宇
 * @create 2023/8/31 14:54
 * @desc
 */
@Data
public class TopicRequest {
    private String name;
    private String cover;
    private String desc;

    public void check(){
        if(ObjectUtils.isEmpty(name) || name.length()>15) {
            throw new CustomException(10000, "专题名称不能为空并且不能大于15个字符");
        }else if(ObjectUtils.isEmpty(desc) || desc.length()>150) {
            throw new CustomException(10000, "专题描述不能为空并且不能大于150个字符");
        }else if(!ValidateUtils.isImageUrl(cover)){
            throw new CustomException(10000, "这不是一个图片链接");
        }
    }

    public Topic toDo(Integer userId){
        Topic topic=new Topic();
        topic.setName(name);
        topic.setDescription(desc);
        topic.setCover(cover);
        Date now = new Date();
        topic.setUpdateAt(now);
        topic.setCreateAt(now);
        topic.setUserId(userId);
        return topic;
    }
}
