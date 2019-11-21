package com.tencent.health.web.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.tencent.health.constant.MessageConstant;
import com.tencent.health.entity.PageResult;
import com.tencent.health.entity.QueryPageBean;
import com.tencent.health.entity.Result;
import com.tencent.health.pojo.CheckGroup;
import com.tencent.health.service.CheckGroupService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 检查组模块
 *
 * @author tttt
 * @date 2019/11/20
 */
@RequestMapping("/checkGroup")
@Controller
public class CheckGroupController {

    @Reference
    private CheckGroupService checkGroupService;

    /**
     * 分页带条件查询
     *
     * @param queryPageBean
     * @return
     */
    @RequestMapping("/findAll")
    @ResponseBody
    public PageResult findAllByPage(@RequestBody QueryPageBean queryPageBean) {
        PageResult all = checkGroupService.findAll(queryPageBean);
        return all;
    }

    /**
     * 添加一个检查组
     *
     * @param checkGroup
     * @return
     */
    @RequestMapping("/addOne")
    @ResponseBody
    public Result add(@RequestBody CheckGroup checkGroup, Integer[] checkitemIds) {
        Result result = new Result();
        try {
            checkGroupService.addOne(checkGroup, checkitemIds);
            result.setMessage(MessageConstant.ADD_CHECKGROUP_SUCCESS);
            result.setFlag(true);
        } catch (Exception e) {
            e.printStackTrace();
            result.setMessage(MessageConstant.ADD_CHECKGROUP_FAIL);
            result.setFlag(false);
        }
        return result;
    }

    /**
     * 通过id值查找一个检查组。
     *
     * @param id
     * @return
     */
    @RequestMapping("/findItemsById")
    @ResponseBody
    public CheckGroup findById(String id) {
        return checkGroupService.findItemsById(id);
    }

    /**
     * 修改检查组
     *
     * @param checkGroup
     * @param checkitemIds
     * @return
     */
    @RequestMapping("/update")
    @ResponseBody
    public Result update(@RequestBody CheckGroup checkGroup, Integer[] checkitemIds) {

        try {
            checkGroupService.update(checkGroup, checkitemIds);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.EDIT_CHECKGROUP_FAIL);
        }
        return new Result(true, MessageConstant.EDIT_CHECKGROUP_SUCCESS);
    }

    /**
     * 删除检查组
     *
     * @param id
     * @return
     */
    @RequestMapping("/delete")
    @ResponseBody
    public Result delete(String id) {
        try {
            checkGroupService.delete(id);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.DELETE_CHECKGROUP_FAIL);
        }
        return new Result(true, MessageConstant.DELETE_CHECKGROUP_SUCCESS);
    }
}
