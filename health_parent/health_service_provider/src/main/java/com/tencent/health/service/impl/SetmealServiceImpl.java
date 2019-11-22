package com.tencent.health.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.tencent.health.constant.RedisConstant;
import com.tencent.health.entity.PageResult;
import com.tencent.health.entity.QueryPageBean;
import com.tencent.health.mapper.SetmealMapper;
import com.tencent.health.pojo.Setmeal;
import com.tencent.health.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.JedisPool;

@Service(interfaceClass = SetmealService.class)
@Transactional
public class SetmealServiceImpl implements SetmealService {

    @Autowired
    private SetmealMapper setmealMapper;

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
        jedisPool.getResource().sadd(RedisConstant.SETMEAL_PIC_DB_RESOURCES, setmeal.getImg());
    }

    @Override
    public void delete(String id) {
        int idInt = Integer.parseInt(id);
        setmealMapper.deleteForeignKey(idInt);
        setmealMapper.delete(idInt);
        //删除缓存中的图片
        Setmeal setmeal = findById(id);
        jedisPool.getResource().srem(RedisConstant.SETMEAL_PIC_DB_RESOURCES, setmeal.getImg());
    }

    @Override
    public Setmeal findById(String id) {

        return setmealMapper.findCheckGroups(Integer.parseInt(id));
    }

    @Override
    public void update(Setmeal setmeal, Integer[] checkGroupIds) {
        setmealMapper.saveOne(setmeal);
        saveSetmealAndCheckGroupId(setmeal.getId(), checkGroupIds);
    }

    private void saveSetmealAndCheckGroupId(Integer id, Integer[] checkGroupIds) {
        for (Integer checkGroupId : checkGroupIds) {
            setmealMapper.saveForeignKey(id, checkGroupId);
        }
    }
}
