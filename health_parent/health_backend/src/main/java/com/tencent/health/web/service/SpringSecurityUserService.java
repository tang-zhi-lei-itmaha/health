package com.tencent.health.web.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.tencent.health.pojo.Permission;
import com.tencent.health.pojo.Role;
import com.tencent.health.pojo.User;
import com.tencent.health.service.UserService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * 权限控制
 *
 * @Author: Tang Zhilei
 * @Date: Create in 19:07 2019/11/28
 */
@Component
public class SpringSecurityUserService implements UserDetailsService {
    @Reference
    private UserService userService;

    /**
     * 通过用户名查询用户，并授予相关的角色和权限
     *
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.findByUsername(username);
        if (user == null) {
            return null;
        }
        //给用户授权与角色
        List<GrantedAuthority> list = new ArrayList<>();
        Set<Role> roleSet = user.getRoleSet();
        for (Role role : roleSet) {
            list.add(new SimpleGrantedAuthority(role.getKeyword()));
            for (Permission permission : role.getPermissionSet()) {
                list.add(new SimpleGrantedAuthority(permission.getKeyword()));
            }
        }
        return new org.springframework.security.core.userdetails.User(username, user.getPassword(), list);
    }
}
