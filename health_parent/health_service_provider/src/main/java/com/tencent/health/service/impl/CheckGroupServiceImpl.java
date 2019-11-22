package com.tencent.health.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.tencent.health.entity.PageResult;
import com.tencent.health.entity.QueryPageBean;
import com.tencent.health.mapper.CheckGroupMapper;
import com.tencent.health.pojo.CheckGroup;
import com.tencent.health.service.CheckGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Service(interfaceClass = CheckGroupService.class)//指定接口，否则事务无法生效
@Transactional
public class CheckGroupServiceImpl implements CheckGroupService {

    @Autowired
    private CheckGroupMapper checkGroupMapper;

    /**
     * 分页查询检查组，
     *
     * @param queryPageBean
     * @return
     */
    @Override
    public PageResult findAll(QueryPageBean queryPageBean) {
        //使用pagehelper查询
        PageHelper.startPage(queryPageBean.getCurrentPage(), queryPageBean.getPageSize());
        String queryString = queryPageBean.getQueryString();
        //排除空格
        if (queryString != null && queryString.contains(" ")) {
            queryString = queryString.replace(" ", "");
        }
        Page<CheckGroup> checkGroupList = checkGroupMapper.findAll(queryString);
        //获得查询出的数据总条数
        long total = checkGroupList.getTotal();
        return new PageResult(total, checkGroupList);
    }

    /**
     * 添加一个检查组
     *
     * @param checkGroup
     */
    @Override
    public void addOne(CheckGroup checkGroup, Integer[] ids) {
        checkGroupMapper.addOne(checkGroup);
        Integer id = checkGroup.getId();
        saveCheckGroupIdAndCheckItemId(id, ids);

    }

    @Override
    public CheckGroup findItemsById(String id) {
        return checkGroupMapper.findById(Integer.parseInt(id));
    }

    /**
     * 修改一个检查组
     *
     * @param checkGroup
     * @param ids
     */
    @Override
    public void update(CheckGroup checkGroup, Integer[] ids) {
        checkGroupMapper.updateOne(checkGroup);
        Integer id = checkGroup.getId();
        //修改外键，先删除外键，再重新添加外键
        checkGroupMapper.deleteForeignKey(id);
        saveCheckGroupIdAndCheckItemId(id, ids);
    }

    /**
     * 删除检查组
     *
     * @param id
     */
    @Override
    public void delete(String id) {
        //因为有外键存在，所以需先删除外键，再删除数据
        checkGroupMapper.deleteForeignKey(Integer.parseInt(id));
        checkGroupMapper.deleteById(Integer.parseInt(id));
    }

    /**
     * 抽取一段重复代码，提高代码的可重用性
     *
     * @param id
     * @param ids
     */
    private void saveCheckGroupIdAndCheckItemId(Integer id, Integer[] ids) {
        for (Integer i : ids) {
            checkGroupMapper.saveItems(id, i);
        }
    }
}
