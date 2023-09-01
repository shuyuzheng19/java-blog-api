package com.zsy.service;

import com.zsy.model.request.BlogAdminFilterRequest;
import com.zsy.model.request.CategoryFilterRequest;
import com.zsy.model.response.PageResult;
import com.zsy.model.vo.CategoryVo;
import com.zsy.model.vo.TagVo;

import java.util.List;
import java.util.Set;

/**
 * @author 郑书宇
 * @create 2023/8/31 1:25
 * @desc
 */
public interface TagService {
    Set<TagVo> getRandomTagList();
    List<TagVo> getAllTagListFormDB();
    PageResult getTagIdBlogList(Integer tid,int page);
    TagVo getTagById(Integer tid);
    TagVo addTag(String name);
    PageResult getAdminCategoryList(CategoryFilterRequest request);
    int updateTag(TagVo tagVo);
    int deleteTag(Integer cid,boolean deleted);
    int deleteTags(List<Integer> cids,boolean deleted);
}
