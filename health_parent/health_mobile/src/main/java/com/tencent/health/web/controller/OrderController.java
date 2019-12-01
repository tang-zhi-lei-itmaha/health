package com.tencent.health.web.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.aliyuncs.exceptions.ClientException;
import com.tencent.health.constant.MessageConstant;
import com.tencent.health.constant.RedisMessageConstant;
import com.tencent.health.entity.Result;
import com.tencent.health.pojo.Order;
import com.tencent.health.service.OrderService;
import com.tencent.health.util.SMSUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import redis.clients.jedis.JedisPool;

import java.util.Map;


/**
 * 预约的相关功能
 *
 * @Author: Tang Zhilei
 * @Date: Create in 11:04 2019/11/26
 */
@Controller
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private JedisPool jedisPool;
    @Reference
    private OrderService orderService;

    /**
     * 提交用户的预约信息
     *
     * @param map
     * @return
     */
    @RequestMapping("/submit")
    @ResponseBody
    public Result orderSubmit(@RequestBody Map map) {
        //校验验证码，校验通过后才会保存预约信息
        String telephone = (String) map.get("telephone");
        String validateCode = (String) map.get("validateCode");
        String validateCodeInRedis = jedisPool.getResource().get(telephone + RedisMessageConstant.SENDTYPE_ORDER);
        //首先校验手机号与验证码是否为空字符串或空
        if (telephone == null || "".equals(telephone) || validateCode == null || "".equals(validateCode)) {
            return new Result(false, MessageConstant.TELEPHONE_VALIDATECODE_NOTNULL);
        } else {
            //验证码校验
            if (validateCode.equals(validateCodeInRedis)) {
                //验证码校验通过，进行保存预约信息操作
                //设置预约类型，微信预约
                map.put("orderType", Order.ORDERTYPE_WEIXIN);
                Result result = null;
                try {
                    result = orderService.save(map);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (result.isFlag()) {
                    //预约成功，发送通知短信
                    try {
                        SMSUtils.sendShortMessage(SMSUtils.ORDER_NOTICE, telephone, (String) map.get("orderDate"));
                    } catch (ClientException e) {
                        e.printStackTrace();
                    }
                }
                //返回预约详情
                return result;
            }
            //验证码校验不通过，直接返回信息
            return new Result(false, MessageConstant.VALIDATECODE_ERROR);
        }
    }

    /**
     * 查询用户的预约信息
     *
     * @param id
     * @return
     */
    @RequestMapping("/findById")
    @ResponseBody
    public Result findById(String id) {
        try {
            Map<String, Object> map = orderService.findById(id);
            return new Result(true, MessageConstant.QUERY_ORDER_SUCCESS, map);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_ORDER_FAIL);
        }
    }
}