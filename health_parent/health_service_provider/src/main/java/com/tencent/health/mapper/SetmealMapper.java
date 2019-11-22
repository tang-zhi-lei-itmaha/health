package com.tencent.health.mapper;

import com.github.pagehelper.Page;
import com.tencent.health.pojo.Setmeal;

public interface SetmealMapper {
    Page<Setmeal> findPageCondition(String queryString);

    void insert(Setmeal setmeal);

    void saveForeignKey(Integer id, Integer checkGroupId);

    void deleteForeignKey(int parseInt);

    void delete(int parseInt);

    Setmeal findById(int id);

    Setmeal findCheckGroups(int parseInt);

    void saveOne(Setmeal setmeal);
}
