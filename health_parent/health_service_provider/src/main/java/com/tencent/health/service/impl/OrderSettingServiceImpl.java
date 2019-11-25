package com.tencent.health.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.tencent.health.mapper.OrderSettingMapper;
import com.tencent.health.pojo.OrderSetting;
import com.tencent.health.service.OrderSettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.*;

@Service(interfaceClass = OrderSettingService.class)
@Transactional
public class OrderSettingServiceImpl implements OrderSettingService {

    @Autowired
    private OrderSettingMapper orderSettingMapper;

    @Override
    public void saveOrder(List<OrderSetting> orderSettingList) {
        if (orderSettingList != null && orderSettingList.size() > 0) {
            for (OrderSetting orderSetting : orderSettingList) {
                //判断当前日期是否已经进行了预约设置
                long count = orderSettingMapper.findCountByOrderDate(orderSetting.getOrderDate());
                if (count > 0) {//已经进行了预约设置，修改设置值
                    orderSettingMapper.editNumberByOrderDate(orderSetting);
                } else {//未进行预约设置，插入数据
                    orderSettingMapper.saveOrder(orderSetting);
                }
            }
        }
    }

    @Override
    public List<Map<String, Object>> getOrderSettingByMonth(String date) {
        String begin = date + "-1";
        String end = date + "-31";
        Map<String, String> map = new HashMap<>();
        map.put("begin", begin);
        map.put("end", end);
        List<OrderSetting> orderSettingList = orderSettingMapper.findCurrentMonthOrder(map);
        List<Map<String, Object>> list = new ArrayList<>();
        //将查询到的数据格式进行整理，与前台数据格式保持一致
        if (orderSettingList != null && orderSettingList.size() > 0) {
            for (OrderSetting orderSetting : orderSettingList) {
                System.out.println(orderSetting.getOrderDate());
                Map<String, Object> data = new HashMap<>();
                data.put("date", orderSetting.getOrderDate().getDate());
                data.put("number", orderSetting.getNumber());
                data.put("reservations", orderSetting.getReservations());
                list.add(data);
            }
        }
        return list;
    }

    @Override
    public void editNumberByDate(OrderSetting orderSetting) {
        Date orderDate = orderSetting.getOrderDate();
        Long count = orderSettingMapper.findCountByOrderDate(orderDate);
        if (count > 0) {
            orderSettingMapper.editNumberByOrderDate(orderSetting);
        } else {
            orderSettingMapper.saveOrder(orderSetting);
        }
    }
}
