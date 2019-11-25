package com.tencent.health.mapper;

import com.tencent.health.pojo.OrderSetting;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface OrderSettingMapper {
    void saveOrder(OrderSetting orderSetting);

    Long findCountByOrderDate(Date orderDate);

    void editNumberByOrderDate(OrderSetting orderSetting);

    List<OrderSetting> findCurrentMonthOrder(Map<String, String> map);
}
