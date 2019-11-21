package com.tencent.health.service;

import com.tencent.health.entity.PageResult;
import com.tencent.health.entity.QueryPageBean;
import com.tencent.health.pojo.CheckGroup;

public interface CheckGroupService {
    PageResult findAll(QueryPageBean queryPageBean);

    void addOne(CheckGroup checkGroup,Integer[] ids);

    CheckGroup findItemsById(String id);

    void update(CheckGroup checkGroup, Integer[] checkitemIds);

    void delete(String id);
}
