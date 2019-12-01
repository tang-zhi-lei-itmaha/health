package com.tencent.health.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.tencent.health.constant.MessageConstant;
import com.tencent.health.entity.Result;
import com.tencent.health.mapper.MemberMapper;
import com.tencent.health.mapper.OrderMapper;
import com.tencent.health.mapper.OrderSettingMapper;
import com.tencent.health.pojo.Member;
import com.tencent.health.pojo.Order;
import com.tencent.health.pojo.OrderSetting;
import com.tencent.health.service.OrderService;
import com.tencent.health.util.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 预约功能service层
 *
 * @Author: Tang Zhilei
 * @Date: Create in 11:15 2019/11/26
 */
@Service(interfaceClass = OrderService.class)
@Transactional
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderSettingMapper orderSettingMapper;
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private MemberMapper memberMapper;

    /**
     * 预约功能
     *
     * @param map
     * @return
     */
    @Override
    public Result save(Map map) throws Exception {
        //  1、检查会员所选择的预约日期是否已经提前进行了预约设置，如果没有设置则无法进行预约
        String orderDate = (String) map.get("orderDate");
        Date date = DateUtils.parseString2Date(orderDate);
        OrderSetting orderSetting = orderSettingMapper.findByOrderDate(date);
        if (orderSetting.getReservations() == null) {
            orderSetting.setReservations(0);
        }
        if (orderSetting == null) {
            return new Result(false, MessageConstant.SELECTED_DATE_CANNOT_ORDER);
        }
        //  2、检查用户所选择的预约日期是否已经约满，如果已经约满则无法预约
        if (orderSetting.getNumber() <= orderSetting.getReservations()) {
            return new Result(false, MessageConstant.ORDER_FULL);
        }
        //  3、检查用户是否重复预约（同一个用户在同一天预约了同一个套餐），如果是重复预约则无法完成再次预约
        String telephone = (String) map.get("telephone");
        Member member = memberMapper.findByPhoneNumber(telephone);
        Order order = null;
        if (member != null) {
            order = new Order(member.getId(), date, Integer.parseInt((String) map.get("setmealId")));
            List<Order> orderList = orderMapper.findByCondition(order);
            if (orderList != null && orderList.size() > 0) {
                //说明该用户是重复预约
                return new Result(false, MessageConstant.HAS_ORDERED);
            } else {//  4、检查当前用户是否为会员，如果是会员则直接完成预约，如果不是会员则自动完成注册并进行预约
                //该用户是会员，但是没有预约，保存预约信息
                order.setOrderStatus(Order.ORDERSTATUS_NO);
                order.setOrderType((String) map.get("orderType"));
                orderMapper.addOne(order);
            }
        } else {
            //根据手机号查询结果为空，说明该用户不是会员,保存会员信息与预约信息
            //保存会员信息
            member = new Member();
            member.setName((String) map.get("name"));
            member.setPhoneNumber(telephone);
            member.setSex((String) map.get("sex"));
            member.setIdCard((String) map.get("idCard"));
            member.setRegTime(date);
            memberMapper.addOne(member);
            //保存预约信息
            order = new Order(member.getId(), date, (String) map.get("orderType"), Order.ORDERSTATUS_NO, Integer.parseInt((String) map.get("setmealId")));
            orderMapper.addOne(order);
        }
        //  5、预约成功，更新当日的已预约人数
        orderSetting.setReservations(orderSetting.getReservations() + 1);
        orderSettingMapper.editReservations(orderSetting);
        return new Result(true, MessageConstant.ORDER_SUCCESS, order.getId());
    }

    /**
     * 查询用户的预约信息
     *
     * @param id
     * @return
     */
    @Override
    public Map<String, Object> findById(String id) throws Exception {
        Map<String, Object> map = orderMapper.findById(Integer.parseInt(id));
        Date orderDate = (Date) map.get("orderDate");
        map.put("orderDate", DateUtils.parseDate2String(orderDate));
        return map;
    }
}
