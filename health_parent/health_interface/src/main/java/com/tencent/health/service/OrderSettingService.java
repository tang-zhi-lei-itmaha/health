package com.tencent.health.service;

import com.tencent.health.pojo.OrderSetting;

import java.util.List;
import java.util.Map;

public interface OrderSettingService {
    void saveOrder(List<OrderSetting> orderSettingList);

    List<Map<String, Object>> getOrderSettingByMonth(String date);

    void editNumberByDate(OrderSetting orderSetting);
}
