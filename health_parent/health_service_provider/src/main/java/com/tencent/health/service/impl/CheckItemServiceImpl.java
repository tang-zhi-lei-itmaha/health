package com.tencent.health.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tencent.health.entity.PageResult;
import com.tencent.health.entity.QueryPageBean;
import com.tencent.health.mapper.CheckItemMapper;
import com.tencent.health.pojo.CheckItem;
import com.tencent.health.service.CheckItemService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service
public class CheckItemServiceImpl implements CheckItemService {
    @Autowired
    private CheckItemMapper checkItemMapper;

    /**
     * 添加一个检查项
     *
     * @param checkItem
     */
    @Override
    public void save(CheckItem checkItem) {
        checkItemMapper.save(checkItem);
    }

    /**
     * 分页查询
     *
     * @param queryPageBean
     * @return
     */
    @Override
    public PageResult findCurrentPage(QueryPageBean queryPageBean) {
        PageHelper.startPage(queryPageBean.getCurrentPage(), queryPageBean.getPageSize());
        String queryString = queryPageBean.getQueryString();
        //如果查询条件中包含空格，将其替换为空字符串
        if (queryString != null) {
            if (queryString.contains(" ")) {
                queryString = queryString.replace(" ", "");
            }
        }
        List<CheckItem> checkItemList = checkItemMapper.findWithCondition(queryString);

        long total = new PageInfo<CheckItem>(checkItemList).getTotal();

        PageResult pageResult = new PageResult();
        pageResult.setRows(checkItemList);
        pageResult.setTotal(total);

        return pageResult;
    }

    /**
     * 通过id查询一个检查项
     *
     * @param id
     * @return
     */
    @Override
    public CheckItem findOne(String id) {
        return checkItemMapper.findOne(Integer.parseInt(id));
    }

    /**
     * 修改检查项
     *
     * @param checkItem
     */
    @Override
    public void update(CheckItem checkItem) {
        checkItemMapper.update(checkItem);
    }

    /**
     * 删除检查项
     *
     * @param id
     * @throws RuntimeException
     */
    @Override
    public void delete(String id) throws RuntimeException {
        long count = checkItemMapper.findCountByCheckItemId(Integer.parseInt(id));
        //先判断检查组中是否已经存在此检查项，如果存在，则无法删除。
        if (count > 0) {
            throw new RuntimeException("检查组中已存在此项，无法删除!");
        } else {
            checkItemMapper.delete(Integer.parseInt(id));
        }
    }

    /**
     * 查询所有
     *
     * @return
     */
    @Override
    public List<CheckItem> findAll() {
        return checkItemMapper.findAll();
    }
}
