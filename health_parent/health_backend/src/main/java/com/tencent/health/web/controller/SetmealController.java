package com.tencent.health.web.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.tencent.health.constant.MessageConstant;
import com.tencent.health.constant.RedisConstant;
import com.tencent.health.entity.PageResult;
import com.tencent.health.entity.QueryPageBean;
import com.tencent.health.entity.Result;
import com.tencent.health.pojo.Setmeal;
import com.tencent.health.service.SetmealService;
import com.tencent.health.util.QiniuUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import redis.clients.jedis.JedisPool;

import java.io.IOException;
import java.util.UUID;

/**
 * 套餐管理模块
 *
 * @author tttt
 * @date 2019/11/21
 */
@RequestMapping("/setmeal")
@Controller
public class SetmealController {

    @Reference
    private SetmealService setmealService;

    @Autowired
    private JedisPool jedisPool;

    /**
     * 分页查询套餐项
     *
     * @param queryPageBean
     * @return
     */
    @PreAuthorize("hasAuthority('SETMEAL_QUERY')")
    @RequestMapping("/findAll")
    @ResponseBody
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean) {
        return setmealService.findPage(queryPageBean);
    }

    /**
     * 图片上传
     *
     * @param multipartFile
     * @return
     */
    @RequestMapping("/upload")
    @ResponseBody
    public Result upload(@RequestParam("imgFile") MultipartFile multipartFile) {
        try {
            //获取原始文件名
            String originalFilename = multipartFile.getOriginalFilename();
            //获取文件后缀名
            String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
            //使用UUID随机产生文件名，防止同名文件覆盖
            String fileName = UUID.randomUUID().toString() + suffix;
            QiniuUtils.upload2Qiniu(multipartFile.getBytes(), fileName);
            jedisPool.getResource().sadd(RedisConstant.SETMEAL_PIC_RESOURCES, fileName);
            return new Result(true, MessageConstant.PIC_UPLOAD_SUCCESS, fileName);
        } catch (IOException e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.PIC_UPLOAD_FAIL);
        }
    }

    /**
     * 套餐添加功能
     *
     * @param setmeal
     * @param checkGroupIds
     * @return
     */
    @PreAuthorize("hasAuthority('SETMEAL_ADD')")
    @RequestMapping("/addOne")
    @ResponseBody
    public Result addOne(@RequestBody Setmeal setmeal, Integer[] checkGroupIds) {
        try {
            setmealService.addOne(setmeal, checkGroupIds);
            jedisPool.getResource().sadd(RedisConstant.SETMEAL_PIC_DB_RESOURCES, setmeal.getImg());
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.ADD_SETMEAL_FAIL);
        }
        return new Result(true, MessageConstant.ADD_SETMEAL_SUCCESS);
    }

    /**
     * 删除一个套餐
     *
     * @param id
     * @return
     */
    @PreAuthorize("hasAuthority('SETMEAL_DELETE')")
    @RequestMapping("/delete")
    @ResponseBody
    public Result delete(String id) {
        try {
            setmealService.delete(id);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.DELETE_SETMEAL_FAIL);
        }
        return new Result(true, MessageConstant.DELETE_SETMEAL_SUCCESS);
    }

    /**
     * 通过id查找套餐及其所拥有的检查组
     *
     * @param id
     * @return
     */
    @RequestMapping("/findOneById")
    @ResponseBody
    public Setmeal findOneById(String id) {
        return setmealService.findById(id);
    }

    /**
     * 编辑套餐
     *
     * @param setmeal
     * @param checkGroupIds
     * @return
     */
    @PreAuthorize("hasAuthority('SETMEAL_EDIT')")
    @RequestMapping("/update")
    @ResponseBody
    public Result update(@RequestBody Setmeal setmeal, Integer[] checkGroupIds) {
        try {
            setmealService.update(setmeal, checkGroupIds);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.EDIT_SETMEAL_FAIL);
        }
        return new Result(true, MessageConstant.EDIT_SETMEAL_SUCCESS);
    }
}
