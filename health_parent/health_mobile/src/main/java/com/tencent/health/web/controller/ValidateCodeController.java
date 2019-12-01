package com.tencent.health.web.controller;

import com.aliyuncs.exceptions.ClientException;
import com.tencent.health.constant.MessageConstant;
import com.tencent.health.constant.RedisMessageConstant;
import com.tencent.health.entity.Result;
import com.tencent.health.util.SMSUtils;
import com.tencent.health.util.ValidateCodeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import redis.clients.jedis.JedisPool;

/**
 * 验证码操作相关功能
 *
 * @Author: Tang Zhilei
 * @Date: Create in 10:35 2019/11/26
 */
@RequestMapping("/validateCode")
@Controller
public class ValidateCodeController {

    @Autowired
    private JedisPool jedisPool;

    /**
     * 预约时发送验证码
     *
     * @param telephone
     * @return
     */
    @RequestMapping("/send4Order")
    @ResponseBody
    public Result sendMessage(String telephone) {
        try {
            String validateCode = ValidateCodeUtils.generateValidateCode4String(6);
            SMSUtils.sendShortMessage(SMSUtils.VALIDATE_CODE, telephone, validateCode);
            //验证码发送成功后，将其保存在缓存中，方便校验，并设定有效时间
            jedisPool.getResource().setex(telephone + RedisMessageConstant.SENDTYPE_ORDER, 60 * 60 * 24, validateCode);
        } catch (ClientException e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.SEND_VALIDATECODE_FAIL);
        }
        return new Result(true, MessageConstant.SEND_VALIDATECODE_SUCCESS);
    }

    /**
     * 登录时发送验证码
     *
     * @param telephone
     * @return
     */
    @RequestMapping("/send4Login")
    @ResponseBody
    public Result send4Login(final String telephone) {
        try {
            //获取一个随机的验证码
            String validateCode = ValidateCodeUtils.generateValidateCode4String(6);
            //发送短信
            SMSUtils.sendShortMessage(SMSUtils.VALIDATE_CODE, telephone, validateCode);
            jedisPool.getResource().setex(telephone + RedisMessageConstant.SENDTYPE_LOGIN, 60 * 60 * 24, validateCode);
        } catch (ClientException e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.SEND_VALIDATECODE_FAIL);
        }
        return new Result(true, MessageConstant.SEND_VALIDATECODE_SUCCESS);
    }
}
