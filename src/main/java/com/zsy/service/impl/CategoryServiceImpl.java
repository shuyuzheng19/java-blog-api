package com.zsy.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zsy.config.ManualJacksonSerializationService;
import com.zsy.constatns.PageConstants;
import com.zsy.constatns.RedisConstants;
import com.zsy.exception.CustomException;
import com.zsy.exception.ErrorEnum;
import com.zsy.mapper.CategoryMapper;
import com.zsy.model.pojo.Category;
import com.zsy.model.request.BlogAdminFilterRequest;
import com.zsy.model.request.CategoryFilterRequest;
import com.zsy.model.response.PageResult;
import com.zsy.model.vo.AdminOtherVo;
import com.zsy.model.vo.BlogAdminVo;
import com.zsy.model.vo.CategoryVo;
import com.zsy.model.vo.TagVo;
import com.zsy.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 郑书宇
 * @create 2023/8/31 1:25
 * @desc
 */
@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryMapper categoryMapper;

    private final RedisTemplate<String,String> redisTemplate;

    private final ManualJacksonSerializationService objectSerialization;

    @Override
    public List<CategoryVo> getCategoryList() {
        boolean flag = redisTemplate.hasKey(RedisConstants.CATEGORY_LIST);
        if(flag) {
            List<String> range = redisTemplate.opsForList().range(RedisConstants.CATEGORY_LIST, 0, -1);
            List<CategoryVo> categoryVos=range.stream().map(r->objectSerialization.deserializeObject(r,CategoryVo.class)).collect(Collectors.toList());
            return categoryVos;
        }else{
            List<CategoryVo> categories = categoryMapper.findAllCategory();
            List<String> list = categories.stream().map(c->objectSerialization.serializeObject(c)).collect(Collectors.toList());
            redisTemplate.opsForList().rightPushAll(RedisConstants.CATEGORY_LIST,list);
            redisTemplate.expire(RedisConstants.CATEGORY_LIST,RedisConstants.CATEGORY_LIST_EXPIRE);
            return categories;
        }
    }

    @Override
    public List<CategoryVo> getCategoryListFormDB() {
        return categoryMapper.findAllCategory();
    }

    @Override
    @Transactional
    public CategoryVo addCategory(String name) {
        if(ObjectUtils.isEmpty(name)){
            throw new CustomException(ErrorEnum.ERROR_EMPTY);
        }
        Category category=new Category();
        category.setName(name);
        Date now = new Date();
        category.setCreateAt(now);
        category.setUpdateAt(now);
        int count = categoryMapper.addCategory(category);
        if(count==0){
            throw new CustomException(ErrorEnum.ERROR_SAVE_CATEGORY_FAIL);
        }
        System.out.println(category);
        redisTemplate.delete(RedisConstants.CATEGORY_LIST);
        CategoryVo categoryVo=new CategoryVo();
        categoryVo.setId(category.getId());
        categoryVo.setName(category.getName());
        return categoryVo;
    }

    @Override
    public PageResult getAdminCategoryList(CategoryFilterRequest request) {
        PageHelper.startPage(request.getPage(), PageConstants.CATEGORY_LIST_COUNT);
        if(request.getStart()!=null&&request.getEnd()!=null) {
            request.setStartDate(new Date(request.getStart()));
            request.setEndDate(new Date(request.getEnd()));
        }
        PageInfo<AdminOtherVo> pageInfo = PageInfo.of(categoryMapper.listCategory(request));
        return PageResult.builder().page(pageInfo.getPageNum()).size(pageInfo.getPageSize()).total(pageInfo.getTotal()).data(pageInfo.getList()).build();
    }

    @Override
    public int updateCategory(CategoryVo categoryVo) {
        if(categoryVo.getId()==null || ObjectUtils.isEmpty(categoryVo.getName())){
            throw new CustomException(ErrorEnum.ERROR_EMPTY);
        }
        return categoryMapper.updateCategory(categoryVo);
    }

    @Override
    public int deleteCategory(Integer cid, boolean deleted) {
        if(cid<0){
            throw new CustomException(ErrorEnum.ERROR_EMPTY);
        }
        return categoryMapper.deleteCategory(cid,deleted);
    }

    @Override
    public int deleteCategories(List<Integer> cids, boolean deleted) {
        if(ObjectUtils.isEmpty(cids)){
            throw new CustomException(ErrorEnum.ERROR_EMPTY);
        }
        return categoryMapper.deleteCategories(cids,deleted);
    }
}
