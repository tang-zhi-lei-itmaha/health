package com.tencent.health.mapper;

import com.tencent.health.pojo.User;

/**
 * @Author: Tang Zhilei
 * @Date: Create in 19:42 2019/11/28
 */
public interface UserMapper {
    User findUserByUsername(String username);
}
