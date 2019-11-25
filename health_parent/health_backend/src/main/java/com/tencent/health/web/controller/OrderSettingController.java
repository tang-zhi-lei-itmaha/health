package com.tencent.health.web.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.tencent.health.constant.MessageConstant;
import com.tencent.health.entity.Result;
import com.tencent.health.pojo.OrderSetting;
import com.tencent.health.service.OrderSettingService;
import com.tencent.health.util.POIUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 预约模块
 *
 * @author tttt
 * @date 2019/11/24
 */
@RequestMapping("/orderSetting")
@Controller
public class OrderSettingController {

    @Reference
    private OrderSettingService orderSettingService;

    /**
     * 文件上传功能
     *
     * @param multipartFile
     * @return
     */
    @RequestMapping("/upload")
    @ResponseBody
    public Result upload(@RequestParam("excelFile") MultipartFile multipartFile) {

        try {
            List<String[]> list = POIUtils.readExcel(multipartFile);
            List<OrderSetting> orderSettingList = new ArrayList<>();
            for (String[] strings : list) {
                orderSettingList.add(new OrderSetting(new Date(strings[0]), Integer.parseInt(strings[1])));
            }
            orderSettingService.saveOrder(orderSettingList);
        } catch (IOException e) {
            e.printStackTrace();
            //文件解析失败
            return new Result(false, MessageConstant.IMPORT_ORDERSETTING_FAIL);
        }
        return new Result(true, MessageConstant.IMPORT_ORDERSETTING_SUCCESS);
    }

    /**
     * 获取当前月份的预约数据
     *
     * @param date
     * @return
     */
    @RequestMapping("/getCurrentMonthOrder")
    @ResponseBody
    public Result getOrderSettingByMonth(String date) {
        try {
            List<Map<String, Object>> list = orderSettingService.getOrderSettingByMonth(date);
            return new Result(true, MessageConstant.QUERY_ORDER_SUCCESS, list);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_ORDER_FAIL);
        }

    }

    /**
     * 修改可预约的人数
     *
     * @param orderSetting
     * @return
     */
    @RequestMapping("/editNumberByDate")
    @ResponseBody
    public Result editNumberByDate(@RequestBody OrderSetting orderSetting) {
        try {
            System.out.println(orderSetting.getOrderDate());
            orderSettingService.editNumberByDate(orderSetting);
            return new Result(true, MessageConstant.ORDERSETTING_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.ORDERSETTING_FAIL);
        }
    }
}
