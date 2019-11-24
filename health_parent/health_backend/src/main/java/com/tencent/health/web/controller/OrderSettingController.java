package com.tencent.health.web.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.tencent.health.constant.MessageConstant;
import com.tencent.health.entity.Result;
import com.tencent.health.pojo.OrderSetting;
import com.tencent.health.service.OrderSettingService;
import com.tencent.health.util.POIUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RequestMapping("/orderSetting")
@Controller
public class OrderSettingController {

    @Reference
    private OrderSettingService orderSettingService;

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
            return new Result(false, MessageConstant.IMPORT_ORDERSETTING_FAIL);
        }
        return new Result(true, MessageConstant.IMPORT_ORDERSETTING_SUCCESS);
    }

}
