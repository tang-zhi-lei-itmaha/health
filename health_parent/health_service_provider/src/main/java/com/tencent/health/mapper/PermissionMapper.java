package com.tencent.health.mapper;

import com.tencent.health.pojo.Permission;

import java.util.Set;

/**
 * @Author: Tang Zhilei
 * @Date: Create in 19:42 2019/11/28
 */
public interface PermissionMapper {
    Set<Permission> findPermissionByRoleId(Integer id);
}
