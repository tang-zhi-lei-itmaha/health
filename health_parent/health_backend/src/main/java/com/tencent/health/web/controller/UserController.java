package com.tencent.health.web.controller;

import com.tencent.health.constant.MessageConstant;
import com.tencent.health.entity.Result;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 会员模块
 *
 * @Author: Tang Zhilei
 * @Date: Create in 15:55 2019/11/30
 */
@Controller
@RequestMapping("/user")
public class UserController {

    /**
     * 获取当前登录用户的用户名
     *
     * @return
     */
    @RequestMapping("/getUsername")
    @ResponseBody
    public Result getUsername() {
        try {
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            String username = user.getUsername();
            return new Result(true, MessageConstant.GET_USERNAME_SUCCESS, username);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.GET_MEMBER_NUMBER_REPORT_FAIL);
        }
    }
}
