package com.tencent.health.service;

import com.tencent.health.entity.PageResult;
import com.tencent.health.entity.QueryPageBean;
import com.tencent.health.pojo.CheckItem;

import java.util.List;

public interface CheckItemService {
    void save(CheckItem checkItem);

    PageResult findCurrentPage(QueryPageBean queryPageBean);

    CheckItem findOne(String id);

    void update(CheckItem checkItem);

    void delete(String id);
}
