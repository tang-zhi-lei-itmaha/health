package com.tencent.health.mapper;

import com.tencent.health.pojo.Member;

/**
 * 会员管理模块
 *
 * @Author: Tang Zhilei
 * @Date: Create in 14:36 2019/11/27
 */
public interface MemberMapper {
    Member findByPhoneNumber(String telephone);

    void addOne(Member member);

    Integer findMemberCountByMonth(String monStr);

    Integer findCountByDate(String reportDate);

    Integer findMemberCount();

    Integer findMemberCountAfterDate(String thisWeekMonday);
}
