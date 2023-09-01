package com.zsy.model.pojo;

import com.zsy.model.vo.SimpleUserVo;
import com.zsy.model.vo.UserVo;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户实体类
 *
 * 用于表示系统中的用户信息。
 *
 * @author 郑书宇
 * @create 2023/8/30 13:53
 */
@Data
public class User implements Serializable {
    /**
     * 用户ID
     */
    private Integer id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 密码
     */
    private String password;

    /**
     * 电子邮件
     */
    private String email;

    /**
     * 头像
     */
    private String icon;

    /**
     * 被删除时间
     */
    private Date deletedAt;

    /**
     * 创建时间
     */
    private Date createAt;

    /**
     * 更新时间
     */
    private Date updateAt;

    /**
     * 角色ID
     */
    private Integer roleId;

    /**
     * 关联的角色信息
     */
    private Role role;

    public UserVo toVo(){
        UserVo userVo=new UserVo();
        userVo.setId(id);
        userVo.setUsername(username);
        userVo.setNickName(nickName);
        userVo.setRole(role.getName());
        userVo.setIcon(icon);
        return userVo;
    }

    public SimpleUserVo toSimpleUserVo(){
        SimpleUserVo simpleUserVo=new SimpleUserVo();
        simpleUserVo.setId(id);
        simpleUserVo.setNickName(nickName);
        return simpleUserVo;
    }
}
