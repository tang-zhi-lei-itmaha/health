package com.tencent.health.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tencent.health.constant.MessageConstant;
import com.tencent.health.entity.PageResult;
import com.tencent.health.entity.QueryPageBean;
import com.tencent.health.entity.Result;
import com.tencent.health.mapper.CheckItemMapper;
import com.tencent.health.pojo.CheckItem;
import com.tencent.health.service.CheckItemService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service
public class CheckItemServiceImpl implements CheckItemService {
    @Autowired
    private CheckItemMapper checkItemMapper;

    @Override
    public Result save(CheckItem checkItem) {
        Integer save = checkItemMapper.save(checkItem);
        Result result = new Result();
        if (save != 0) {
            result.setFlag(true);
            result.setMessage(MessageConstant.ADD_CHECKITEM_SUCCESS);
        } else {
            result.setFlag(false);
            result.setMessage(MessageConstant.ADD_CHECKITEM_FAIL);
        }
        return result;
    }

    @Override
    public PageResult findCurrentPage(QueryPageBean queryPageBean) {

        PageHelper.startPage(queryPageBean.getCurrentPage(), queryPageBean.getPageSize());
        List<CheckItem> checkItemList = checkItemMapper.findWithCondition(queryPageBean);
        for (CheckItem checkItem : checkItemList) {
            System.out.println(checkItem);
        }

        PageInfo<CheckItem> checkItemPageInfo = new PageInfo<>(checkItemList);
        long total = checkItemPageInfo.getTotal();
        System.out.println(total);

        PageResult pageResult = new PageResult();
        pageResult.setRows(checkItemList);
        pageResult.setTotal(total);

        return pageResult;
    }
}
