package com.wyett.redis.lock.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author : wyettLei
 * @date : Created in 2020/4/1 16:09
 * @description: TODO
 */

@RestController
public class LockControllerV3 {
    private static final Logger Log = LoggerFactory.getLogger(LockControllerV3.class);

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @RequestMapping("/deproduct3")
    public String deproduct() {
        // 利用setnx，如果不存在则设置成功，表示获取锁成功
        String Lockkey = "lockkey";
        boolean isLocked = stringRedisTemplate.opsForValue().setIfAbsent(Lockkey, String.valueOf(1));

        // 获取锁失败
        if (!isLocked) {
            return "get product lock filed";
        }

        int productNum = Integer.parseInt(stringRedisTemplate.opsForValue().get("product"));
        if (productNum > 0) {
            int realProductNum = productNum - 1;
            stringRedisTemplate.opsForValue().set("product", realProductNum + "");
            System.out.println("减库存成功，剩余库存：" + realProductNum);
        } else {
            System.out.println("减库存失败，库存不足");
        }

        stringRedisTemplate.delete(Lockkey);

        return "deproduct end";
    }
}
