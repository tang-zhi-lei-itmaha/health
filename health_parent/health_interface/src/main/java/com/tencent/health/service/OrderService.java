package com.tencent.health.service;

import com.tencent.health.entity.Result;

import java.util.Map;

/**
 * 预约功能接口
 *
 * @Author: Tang Zhilei
 * @Date: Create in 11:14 2019/11/26
 */
public interface OrderService {
    Result save(Map map) throws Exception;

    Map<String, Object> findById(String id) throws Exception;
}
