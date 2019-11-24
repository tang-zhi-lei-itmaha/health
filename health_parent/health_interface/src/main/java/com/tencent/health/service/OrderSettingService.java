package com.tencent.health.service;

import com.tencent.health.pojo.OrderSetting;

import java.util.List;

public interface OrderSettingService {
    void saveOrder(List<OrderSetting> orderSettingList);
}
