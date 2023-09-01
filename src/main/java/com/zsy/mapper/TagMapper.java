package com.zsy.mapper;

import com.zsy.model.pojo.Tag;
import com.zsy.model.request.CategoryFilterRequest;
import com.zsy.model.vo.AdminOtherVo;
import com.zsy.model.vo.BlogVo;
import com.zsy.model.vo.CategoryVo;
import com.zsy.model.vo.TagVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author 郑书宇
 * @create 2023/8/31 1:22
 * @desc
 */
public interface TagMapper {
    List<TagVo> findAllTag();
    TagVo findById(Integer tid);
    List<BlogVo> findBlogByTagId(Integer tid);
    int addTag(Tag tag);
    List<AdminOtherVo> listTag(@Param("request") CategoryFilterRequest request);
    int updateTag(TagVo tagVo);
    int deleteTag(Integer cid,boolean deleted);
    int deleteTags(List<Integer> cids,boolean deleted);
}
