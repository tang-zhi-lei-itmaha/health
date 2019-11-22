package com.tencent.health.jobs;

import com.tencent.health.constant.RedisConstant;
import com.tencent.health.util.QiniuUtils;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.JedisPool;

import java.util.Set;

/**
 * 自定义Job实现定时清理垃圾图片
 *
 * @author tttt
 * @date 2019/11/21
 */
public class ClearImgJob {

    @Autowired
    private JedisPool jedisPool;

    public void clearImg() {
        //根据Redis中保存的两个set集合进行差值计算，获得垃圾图片名称集合
        Set<String> set = jedisPool.getResource().sdiff(RedisConstant.SETMEAL_PIC_RESOURCES, RedisConstant.SETMEAL_PIC_DB_RESOURCES);
        if (set != null) {//不等于空，说明有垃圾图片存在
            for (String picName : set) {
                //删除云服务器中的垃圾图片
                QiniuUtils.deleteFileFromQiniu(picName);
                //删除redis集合中的垃圾图片
                jedisPool.getResource().srem(RedisConstant.SETMEAL_PIC_RESOURCES, picName);
            }
        }
    }
}
