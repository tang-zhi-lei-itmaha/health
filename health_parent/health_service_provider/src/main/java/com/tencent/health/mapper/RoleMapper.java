package com.tencent.health.mapper;

import com.tencent.health.pojo.Role;

import java.util.Set;

/**
 * @Author: Tang Zhilei
 * @Date: Create in 19:42 2019/11/28
 */
public interface RoleMapper {
    Set<Role> findRoleByUserId(Integer id);
}
