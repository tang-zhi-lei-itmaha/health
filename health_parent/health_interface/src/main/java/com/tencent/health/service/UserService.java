package com.tencent.health.service;

import com.tencent.health.pojo.User;

/**
 * 操作用户相关的接口
 *
 * @Author: Tang Zhilei
 * @Date: Create in 18:26 2019/11/28
 */
public interface UserService {
    User findByUsername(String username);
}
