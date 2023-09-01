package com.zsy.mapper;

import com.zsy.model.pojo.Category;
import com.zsy.model.pojo.Role;
import com.zsy.model.pojo.Tag;
import com.zsy.model.vo.CategoryVo;
import com.zsy.model.vo.SimpleTopicVo;
import com.zsy.model.vo.SimpleUserVo;
import com.zsy.model.vo.TagVo;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 角色表操作的数据访问接口
 *
 * 该接口定义了对角色表进行查询操作的方法。
 *
 * @Author 郑书宇
 * @CreateDate 2023/8/30 14:07
 */
@Repository
public interface SimpleQueryMapper {
    /**
     * 根据ID查找角色
     *
     * @param id 角色ID
     * @return 如果找到匹配的角色，返回对应的角色对象；否则返回 null
     */
    @Select("select id, name from roles where id = #{id}")
    Role findByRoleId(Integer id);

    /**
     * 根据ID查找分类
     *
     * @param id 分类ID
     * @return 如果找到匹配的分类，返回对应的分类对象；否则返回 null
     */
    @Select("select id, name from categories where id = #{id}")
    CategoryVo findByCategoryId(Integer id);

    /**
     * 根据博客ID查找对应的标签
     *
     * @param blogId 博客ID
     * @return 如果找到匹配的标签，返回对应的标签列表；否则返回 null
     */
    @Select("select id, name from tags t inner join blogs_tags bt on t.id=bt.tag_id where t.deleted_at is null and bt.blog_id=#{blogId}")
    List<TagVo> findByBlogTagIds(Integer blogId);

    @Select("select id,nick_name from users where deleted_at is null and id = #{userId}")
    SimpleUserVo findByUserId(Integer userId);

    @Select("select id,name from topics where deleted_at is null and id=#{topicId}")
    SimpleTopicVo findByTopicId(Integer topicId);
}
