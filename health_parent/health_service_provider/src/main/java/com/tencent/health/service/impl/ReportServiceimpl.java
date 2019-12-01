package com.tencent.health.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.tencent.health.mapper.OrderMapper;
import com.tencent.health.pojo.Order;
import com.tencent.health.service.MemberService;
import com.tencent.health.service.ReportService;
import com.tencent.health.util.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 报表数据生成服务
 *
 * @Author: Tang Zhilei
 * @Date: Create in 20:03 2019/11/30
 */
@Service(interfaceClass = ReportService.class)
@Transactional
public class ReportServiceimpl implements ReportService {
    @Autowired
    private MemberService memberService;
    @Autowired
    private OrderMapper orderMapper;

    @Override
    public Map<String, Object> getBusinessReportData() throws Exception {

        Map<String, Object> map = new HashMap<>();
        //获取报表的生成日期
        String reportDate = DateUtils.parseDate2String(DateUtils.getToday());
        System.out.println(reportDate);
        //获取今日新增会员数
        Integer todayNewMember = memberService.findMemberByDate(reportDate);
        //获取总会员数
        Integer totalMember = memberService.findMemberCount();
        //获取本周新增会员数
        String thisWeekMonday = DateUtils.parseDate2String(DateUtils.getThisWeekMonday());
        Integer thisWeekNewMember = memberService.findMemberCountAfterDate(thisWeekMonday);
        //获取本月新增会员数
        String firstDay4ThisMonth = DateUtils.parseDate2String(DateUtils.getFirstDay4ThisMonth());
        Integer thisMonthNewMember = memberService.findMemberCountAfterDate(firstDay4ThisMonth);
        //获取今日预约数
        Integer todayOrderNumber = orderMapper.findOrderCountByDate(reportDate);
        //获取今日就诊数
        Map<String, String> params = new HashMap<>();
        params.put("orderDate", reportDate);
        params.put("orderStatus", Order.ORDERSTATUS_YES);
        Integer todayVisitsNumber = orderMapper.findCountOrderStatusByDate(params);
        //获取本周预约数
        params.put("orderDate", thisWeekMonday);
        params.put("orderStatus", null);
        Integer thisWeekOrderNumber = orderMapper.findCountAfterDate(params);
        //获取本周就诊数
        params.put("orderDate", thisWeekMonday);
        params.put("orderStatus", Order.ORDERSTATUS_YES);
        Integer thisWeekVisitsNumber = orderMapper.findCountAfterDate(params);
        //获取本月预约数
        params.put("orderDate", firstDay4ThisMonth);
        params.put("orderStatus", null);
        Integer thisMonthOrderNumber = orderMapper.findCountAfterDate(params);
        //获取本月就诊数
        params.put("orderDate", firstDay4ThisMonth);
        params.put("orderStatus", Order.ORDERSTATUS_YES);
        Integer thisMonthVisitsNumber = orderMapper.findCountAfterDate(params);
        //获取热门套餐详情
        List<Map<String, Object>> hotSetmeal = orderMapper.findHotSetmeal();
        //封装数据
        map.put("reportDate", reportDate);
        map.put("todayNewMember", todayNewMember);
        map.put("totalMember", totalMember);
        map.put("thisWeekNewMember", thisWeekNewMember);
        map.put("thisMonthNewMember", thisMonthNewMember);
        map.put("todayOrderNumber", todayOrderNumber);
        map.put("todayVisitsNumber", todayVisitsNumber);
        map.put("thisWeekOrderNumber", thisWeekOrderNumber);
        map.put("thisWeekVisitsNumber", thisWeekVisitsNumber);
        map.put("thisMonthOrderNumber", thisMonthOrderNumber);
        map.put("thisMonthVisitsNumber", thisMonthVisitsNumber);
        map.put("hotSetmeal", hotSetmeal);
        return map;
    }
}
