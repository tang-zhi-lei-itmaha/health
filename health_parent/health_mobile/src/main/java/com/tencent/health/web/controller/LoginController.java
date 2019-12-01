package com.tencent.health.web.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.tencent.health.constant.MessageConstant;
import com.tencent.health.constant.RedisMessageConstant;
import com.tencent.health.entity.Result;
import com.tencent.health.pojo.Member;
import com.tencent.health.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import redis.clients.jedis.JedisPool;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.Map;

/**
 * 用户登录控制相关功能
 *
 * @Author: Tang Zhilei
 * @Date: Create in 19:54 2019/11/27
 */
@RequestMapping("/login")
@Controller
public class LoginController {
    @Autowired
    private JedisPool jedisPool;
    @Reference
    private MemberService memberService;

    /**
     * 登录功能
     *
     * @param response
     * @param map
     * @return
     */
    @RequestMapping("/check")
    @ResponseBody
    public Result check(HttpServletResponse response, @RequestBody Map<String, String> map) {
        String telephone = map.get("telephone");
        //获取redis中保存的登录yanzhengma
        String validateCodeInRedis = jedisPool.getResource().get(telephone + RedisMessageConstant.SENDTYPE_LOGIN);
        //比对用户提交的验证码与缓存中的验证码
        if (validateCodeInRedis != null && validateCodeInRedis.equals(map.get("validateCode"))) {
            //相同，检验用户是否已经注册
            Member member = memberService.findMemberByTelephone(telephone);
            if (member == null) {
                //未注册，先为用户注册
                member = new Member();
                member.setRegTime(new Date());
                member.setPhoneNumber(telephone);
                memberService.addMember(member);
            }
            //已经注册，登录成功，将用户的电话号码存入cookie，以便跟踪用户信息
            Cookie cookie = new Cookie("login_member_telephone", telephone);
            response.addCookie(cookie);
            //保存会员信息到redis中
            String json = JSON.toJSON(member).toString();
            jedisPool.getResource().setex(telephone, 60 * 60 * 30, json);
            return new Result(true, MessageConstant.LOGIN_SUCCESS);
        }
        System.out.println(validateCodeInRedis);
        System.out.println(map.get("validateCode"));
        //不同，返回提示信息
        return new Result(false, MessageConstant.VALIDATECODE_ERROR);
    }
}
