package com.tencent.health.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.tencent.health.mapper.MemberMapper;
import com.tencent.health.pojo.Member;
import com.tencent.health.service.MemberService;
import com.tencent.health.util.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 会员相关操作
 *
 * @Author: Tang Zhilei
 * @Date: Create in 20:20 2019/11/27
 */
@Service(interfaceClass = MemberService.class)
@Transactional
public class MemberServiceImpl implements MemberService {
    @Autowired
    private MemberMapper memberMapper;

    /**
     * 通过电话号码查找会员
     *
     * @param telephone
     * @return
     */
    @Override
    public Member findMemberByTelephone(String telephone) {
        return memberMapper.findByPhoneNumber(telephone);
    }

    /**
     * 添加一个会员
     *
     * @param member
     */
    @Override
    public void addMember(Member member) {
        //先判断用户是否设置了密码，若设置了密码，则将明文密码加密后保存
        String password = member.getPassword();
        if (password != null) {
            member.setPassword(MD5Utils.md5(password));
        }
        memberMapper.addOne(member);
    }

    /**
     * 获取前n个月的会员数量
     *
     * @param month
     * @return
     */
    @Override
    public List<Integer> findMemberCountByMonth(List<String> month) {
        List<Integer> memberCount = new ArrayList<>();
        for (String mon : month) {
            String monStr = mon + ".31";
            Integer member = memberMapper.findMemberCountByMonth(monStr);
            memberCount.add(member);
        }
        return memberCount;
    }

    /**
     * 查询当日新增成员
     *
     * @param reportDate
     * @return
     */
    @Override
    public Integer findMemberByDate(String reportDate) {
        return memberMapper.findCountByDate(reportDate);
    }

    /**
     * 查询所有的会员总数
     *
     * @return
     */
    @Override
    public Integer findMemberCount() {

        return memberMapper.findMemberCount();
    }

    /**
     * 查询本周新增会员数
     *
     * @param thisWeekMonday
     * @return
     */
    @Override
    public Integer findMemberCountAfterDate(String thisWeekMonday) {
        return memberMapper.findMemberCountAfterDate(thisWeekMonday);
    }
}
