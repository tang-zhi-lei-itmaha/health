package com.tencent.health.service;

import com.tencent.health.entity.PageResult;
import com.tencent.health.entity.QueryPageBean;
import com.tencent.health.entity.Result;
import com.tencent.health.pojo.CheckItem;

public interface CheckItemService {
    Result save(CheckItem checkItem);

    PageResult findCurrentPage(QueryPageBean queryPageBean);
}
