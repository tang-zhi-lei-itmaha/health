package com.tencent.health.service;

import com.tencent.health.pojo.Member;

import java.util.List;

/**
 * @Author: Tang Zhilei
 * @Date: Create in 20:09 2019/11/27
 */
public interface MemberService {

    Member findMemberByTelephone(String telephone);

    void addMember(Member member);

    List<Integer> findMemberCountByMonth(List<String> month);

    Integer findMemberByDate(String reportDate);

    Integer findMemberCount();

    Integer findMemberCountAfterDate(String thisWeekMonday);
}
