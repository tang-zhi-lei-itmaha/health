package com.tencent.health.mapper;

import com.tencent.health.entity.QueryPageBean;
import com.tencent.health.pojo.CheckItem;

import java.util.List;

public interface CheckItemMapper {
    Integer save(CheckItem checkItem);

    List<CheckItem> findWithCondition(QueryPageBean queryPageBean);

}
