package com.tencent.health.web.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.tencent.health.constant.MessageConstant;
import com.tencent.health.entity.PageResult;
import com.tencent.health.entity.QueryPageBean;
import com.tencent.health.entity.Result;
import com.tencent.health.pojo.CheckItem;
import com.tencent.health.service.CheckItemService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 检查项模块
 *
 * @author tttt
 * @date 2019/11/18
 */
@RequestMapping("/checkItem")
@Controller
public class CheckItemController {
    @Reference
    private CheckItemService checkItemService;

    /**
     * 添加一个检查项
     *
     * @param checkItem
     * @return
     */
    @RequestMapping("/add")
    @ResponseBody
    public Result addOne(@RequestBody CheckItem checkItem) {
        Result result = checkItemService.save(checkItem);
        return result;
    }

    /**
     * @return
     */
    @RequestMapping("/findAll")
    @ResponseBody
    public Result findAll(@RequestBody QueryPageBean queryPageBean) {

        System.out.println(queryPageBean.getCurrentPage() + "\t" + queryPageBean.getQueryString() + "\t" + queryPageBean.getPageSize());
        Result result = new Result();
        try {
            PageResult currentPage = checkItemService.findCurrentPage(queryPageBean);
            result.setFlag(true);
            result.setData(currentPage);
            result.setMessage(MessageConstant.QUERY_CHECKITEM_SUCCESS);
        } catch (Exception e) {
            result.setMessage(MessageConstant.QUERY_CHECKITEM_FAIL);
            result.setFlag(false);
        }
        return result;
    }
}
