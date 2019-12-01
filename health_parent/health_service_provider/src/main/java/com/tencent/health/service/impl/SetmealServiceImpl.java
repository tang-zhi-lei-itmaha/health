package com.tencent.health.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.tencent.health.constant.RedisConstant;
import com.tencent.health.entity.PageResult;
import com.tencent.health.entity.QueryPageBean;
import com.tencent.health.mapper.OrderMapper;
import com.tencent.health.mapper.SetmealMapper;
import com.tencent.health.pojo.Setmeal;
import com.tencent.health.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.JedisPool;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service(interfaceClass = SetmealService.class)
@Transactional
public class SetmealServiceImpl implements SetmealService {

    @Autowired
    private SetmealMapper setmealMapper;
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private JedisPool jedisPool;

    @Override
    public PageResult findPage(QueryPageBean queryPageBean) {

        PageHelper.startPage(queryPageBean.getCurrentPage(), queryPageBean.getPageSize());
        String queryString = queryPageBean.getQueryString();
        if (queryString != null && queryString.contains(" ")) {
            queryString = queryString.replace(" ", "");
        }
        Page<Setmeal> setmeals = setmealMapper.findPageCondition(queryString);
        return new PageResult(setmeals.getTotal(), setmeals);
    }

    @Override
    public void addOne(Setmeal setmeal, Integer[] checkGroupIds) {
        setmealMapper.insert(setmeal);
        Integer id = setmeal.getId();
        saveSetmealAndCheckGroupId(id, checkGroupIds);
    }

    @Override
    public void delete(String id) {
        int idInt = Integer.parseInt(id);
        setmealMapper.deleteForeignKey(idInt);
        //删除缓存中的图片
        Setmeal setmeal = findById(id);
        jedisPool.getResource().srem(RedisConstant.SETMEAL_PIC_DB_RESOURCES, setmeal.getImg());
        setmealMapper.delete(idInt);
    }

    @Override
    public Setmeal findById(String id) {

        return setmealMapper.findCheckGroups(Integer.parseInt(id));
    }

    @Override
    public void update(Setmeal setmeal, Integer[] checkGroupIds) {
        Integer id = setmeal.getId();
        setmealMapper.saveOne(setmeal);
        setmealMapper.deleteForeignKey(id);
        saveSetmealAndCheckGroupId(id, checkGroupIds);
    }

    @Override
    public List<Setmeal> findAll() {
        return setmealMapper.findPageCondition("");
    }

    @Override
    public Setmeal findCheckGroupsAndCheckItemsById(String id) {
        return setmealMapper.findCheckGroupsAndCheckItemsById(Integer.parseInt(id));
    }

    @Override
    public Map<String, Object> getSetmealReport() {
        Map<String, Object> map = new HashMap<>();
//        List<Setmeal> setmeals = setmealMapper.findPageCondition(null);
        List<String> setmealNames = new ArrayList<>();//所有套餐名称的集合
    /*    List<Map<String, Object>> setmealCounts = new ArrayList<>();

        for (Setmeal setmeal : setmeals) {
            Map<String, Object> setmealCount = new HashMap<>();//每项套餐预约人数的集合
            Integer count = orderMapper.findCountBySetmealId(setmeal.getId());
            setmealCount.put("value", count);
            setmealCount.put("name", setmeal.getName());
            setmealNames.add(setmeal.getName());
            setmealCounts.add(setmealCount);
        }*/
        List<Map<String, Object>> setmealCount = setmealMapper.findSetmealCount();
        for (Map<String, Object> stringObjectMap : setmealCount) {
            String name = (String) stringObjectMap.get("name");
            setmealNames.add(name);
        }

        map.put("setmealNames", setmealNames);
        map.put("setmealCount", setmealCount);
        return map;
    }

    private void saveSetmealAndCheckGroupId(Integer id, Integer[] checkGroupIds) {
        for (Integer checkGroupId : checkGroupIds) {
            setmealMapper.saveForeignKey(id, checkGroupId);
        }
    }
}
