package com.tencent.health.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.tencent.health.mapper.PermissionMapper;
import com.tencent.health.mapper.RoleMapper;
import com.tencent.health.mapper.UserMapper;
import com.tencent.health.pojo.Permission;
import com.tencent.health.pojo.Role;
import com.tencent.health.pojo.User;
import com.tencent.health.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

/**
 * 用户服务
 *
 * @Author: Tang Zhilei
 * @Date: Create in 18:27 2019/11/28
 */
@Service(interfaceClass = UserService.class)
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private PermissionMapper permissionMapper;

    /**
     * 根据用户的username查询用户的所有信息
     * 和用户关联的角色信息及角色关联的权限信息
     *
     * @param username
     * @return
     */
    @Override
    public User findByUsername(String username) {
        //先查询用户的基本信息
        User user = userMapper.findUserByUsername(username);
        if (user == null) {
            return null;
        }
        //根据用户id查询用户所拥有的角色
        Set<Role> roles = roleMapper.findRoleByUserId(user.getId());
        if (roles == null || roles.size() <= 0) {
            return user;
        }
        //根据角色id查询角色所包含的权限
        for (Role role : roles) {
            Set<Permission> permissions = permissionMapper.findPermissionByRoleId(role.getId());
            role.setPermissionSet(permissions);//让角色关联权限
        }
        //让用户关联角色
        user.setRoleSet(roles);
        return user;
    }
}
