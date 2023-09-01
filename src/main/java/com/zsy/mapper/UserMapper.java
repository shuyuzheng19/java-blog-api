package com.zsy.mapper;

import com.zsy.model.pojo.User;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * 用户表操作的数据访问接口
 *
 * 该接口定义了对用户表进行增、删、改、查等操作的方法。
 *
 * @Author 郑书宇
 * @CreateDate 2023/8/30 13:58
 */
@Repository
public interface UserMapper {
    /**
     * 根据ID查找用户
     *
     * @param id 用户ID
     * @return 如果找到匹配的用户，返回对应的用户对象；否则返回空的 Optional 对象
     */
    User findById(Integer id);

    /**
     * 根据用户名查找用户
     *
     * @param username 用户名
     * @return 如果找到匹配的用户，返回对应的用户对象；否则返回空的 Optional 对象
     */
    Optional<User> findByUsername(String username);

    /**
     * 检查用户名是否存在
     *
     * @param username 待检查的用户名
     * @return 如果用户名存在，返回 true；否则返回 false
     */
    boolean existsUsername(String username);

    /**
     * 保存用户信息
     *
     * @param user 待保存的用户对象
     * @return 是否保存成功
     */
    int saveUser(User user);

    /**
     * 更新用户信息
     *
     * @param user 待更新的用户对象
     * @return 是否更新成功
     */
    int updateUser(User user);
}
