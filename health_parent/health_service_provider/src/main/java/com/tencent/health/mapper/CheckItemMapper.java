package com.tencent.health.mapper;

import com.tencent.health.pojo.CheckItem;

import java.util.List;

public interface CheckItemMapper {
    Integer save(CheckItem checkItem);

    List<CheckItem> findWithCondition(String queryString);

    CheckItem findOne(int parseInt);

    void update(CheckItem checkItem);

    void delete(int parseInt);

    long findCountByCheckItemId(int id);
}
