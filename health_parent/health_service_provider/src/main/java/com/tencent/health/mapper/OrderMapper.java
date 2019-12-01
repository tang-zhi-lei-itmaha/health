package com.tencent.health.mapper;

import com.tencent.health.pojo.Order;

import java.util.List;
import java.util.Map;

/**
 * 预约功能的接口
 *
 * @Author: Tang Zhilei
 * @Date: Create in 16:27 2019/11/26
 */
public interface OrderMapper {
    List<Order> findByCondition(Order order);

    void addOne(Order order);

    Map<String, Object> findById(int parseInt);

    Integer findCountBySetmealId(Integer id);

    Integer findOrderCountByDate(String reportDate);

    Integer findCountOrderStatusByDate(Map<String,String> params);

    Integer findCountAfterDate(Map<String,String> params);

    Integer findCountBeforeDate(Map<String,String> parameters);

    List<Map<String, Object>> findHotSetmeal();
}
