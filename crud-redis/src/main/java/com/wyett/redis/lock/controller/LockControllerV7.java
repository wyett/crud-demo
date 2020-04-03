package com.wyett.redis.lock.controller;

import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author : wyettLei
 * @date : Created in 2020/4/1 16:09
 * @description: use redisson
 */

@RestController
public class LockControllerV7 {
    private static final Logger Log = LoggerFactory.getLogger(LockControllerV7.class);

    @Autowired
    private Redisson redisson;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @RequestMapping("/deproduct7")
    public String deproduct() {

        String lockKey = "lockKey";
        RLock redissonLock = redisson.getLock(lockKey);

        try {
            // 获取锁，并实现对锁的续命
            redissonLock.lock();

            int productNum = Integer.parseInt(stringRedisTemplate.opsForValue().get("product"));
            if (productNum > 0) {
                int realProductNum = productNum - 1;
                stringRedisTemplate.opsForValue().set("product", realProductNum + "");
                System.out.println("减库存成功，剩余库存：" + realProductNum);
            } else {
                System.out.println("减库存失败，库存不足");
            }
        } finally {
            redissonLock.unlock();
        }
        return "deproduct end";
    }
}
