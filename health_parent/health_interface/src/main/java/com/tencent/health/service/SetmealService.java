package com.tencent.health.service;

import com.tencent.health.entity.PageResult;
import com.tencent.health.entity.QueryPageBean;
import com.tencent.health.pojo.Setmeal;

import java.util.List;
import java.util.Map;

public interface SetmealService {
    PageResult findPage(QueryPageBean queryPageBean);

    void addOne(Setmeal setmeal, Integer[] checkGroupIds);

    void delete(String id);

    Setmeal findById(String id);

    void update(Setmeal setmeal, Integer[] checkGroupIds);

    List<Setmeal> findAll();

    Setmeal findCheckGroupsAndCheckItemsById(String id);

    Map<String, Object> getSetmealReport();
}
