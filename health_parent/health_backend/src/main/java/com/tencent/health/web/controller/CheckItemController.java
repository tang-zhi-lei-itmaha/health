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

import java.util.List;

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
        Result result = new Result();
        try {
            checkItemService.save(checkItem);
            result.setMessage(MessageConstant.ADD_CHECKITEM_SUCCESS);
            result.setFlag(true);
        } catch (Exception e) {
            result.setMessage(MessageConstant.ADD_CHECKITEM_FAIL);
            result.setFlag(false);
        }
        return result;
    }

    /**
     * 分页条件查询功能
     *
     * @return
     */
    @RequestMapping("/findAll")
    @ResponseBody
    public Result findAll(@RequestBody QueryPageBean queryPageBean) {

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

    @RequestMapping("/findOne")
    @ResponseBody
    public CheckItem findOne(String id) {
        return checkItemService.findOne(id);
    }

    /**
     * 编辑一个检查项
     *
     * @param checkItem
     * @return
     */
    @RequestMapping("/update")
    @ResponseBody
    public Result update(@RequestBody CheckItem checkItem) {
        Result result = new Result();
        try {
            checkItemService.update(checkItem);
            result.setFlag(true);
            result.setMessage(MessageConstant.EDIT_CHECKITEM_SUCCESS);
        } catch (Exception e) {
            result.setFlag(false);
            result.setMessage(MessageConstant.EDIT_CHECKITEM_FAIL);
        }
        return result;
    }

    /**
     * 根据id删除一个检查项
     *
     * @param id
     * @return
     */
    @RequestMapping("/delete")
    @ResponseBody
    public Result delete(String id) {
        Result result = new Result();
        try {
            checkItemService.delete(id);
            result.setFlag(true);
            result.setMessage(MessageConstant.DELETE_CHECKITEM_SUCCESS);
        } catch (Exception e) {
            result.setFlag(false);
            result.setMessage(MessageConstant.DELETE_CHECKITEM_FAIL);
        }
        return result;
    }

    /**
     * 查询所有检查项
     *
     * @return
     */
    @RequestMapping("/getAll")
    @ResponseBody
    public List<CheckItem> getAll() {
        return checkItemService.findAll();
    }
}
