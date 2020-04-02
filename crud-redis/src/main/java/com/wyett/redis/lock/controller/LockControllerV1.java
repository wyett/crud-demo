package com.wyett.redis.lock.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author : wyettLei
 * @date : Created in 2020/4/2 21:44
 * @description: TODO
 */

@RestController
public class LockControllerV1 {
    private static final Logger Log = LoggerFactory.getLogger(LockControllerV1.class);

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @RequestMapping("/deproduct")
    public void deproduct() {
        int productNum = Integer.parseInt(stringRedisTemplate.opsForValue().get("product"));
        if (productNum > 0) {
            int realProductNum = productNum - 1;
            stringRedisTemplate.opsForValue().set("product",realProductNum + "");
            System.out.println("减库存成功，剩余库存：" + realProductNum);
        } else {
            System.out.println("减库存失败，库存不足");
        }
    }
}
