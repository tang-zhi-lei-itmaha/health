package com.tencent.health.mapper;

import com.github.pagehelper.Page;
import com.tencent.health.pojo.Setmeal;

import java.util.List;
import java.util.Map;

public interface SetmealMapper {
    Page<Setmeal> findPageCondition(String queryString);

    void insert(Setmeal setmeal);

    void saveForeignKey(Integer id, Integer checkGroupId);

    void deleteForeignKey(int parseInt);

    void delete(int parseInt);

    Setmeal findById(int id);

    Setmeal findCheckGroups(int parseInt);

    void saveOne(Setmeal setmeal);

    Setmeal findCheckGroupsAndCheckItemsById(int parseInt);

    List<Map<String, Object>> findSetmealCount();
}
