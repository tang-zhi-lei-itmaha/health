package com.tencent.health.mapper;

import com.github.pagehelper.Page;
import com.tencent.health.pojo.CheckGroup;

public interface CheckGroupMapper {
    Page<CheckGroup> findAll(String queryPageBean);

    void addOne(CheckGroup checkGroup);

    /**
     * 给检查组添加检查项
     *
     * @param id
     * @param ids
     */
    void saveItems(Integer id, Integer ids);

    CheckGroup findById(int parseInt);

    void updateOne(CheckGroup checkGroup);

    /**
     * 删除检查组的所有检查项
     *
     * @param id
     */
    void deleteForeignKey(Integer id);

    /**
     * 删除检查组
     *
     * @param parseInt
     */
    void deleteById(int parseInt);
}
