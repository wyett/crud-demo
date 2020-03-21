package com.wyett.redis.springbootrediscluster.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author : wyettLei
 * @date : Created in 2020/3/21 19:50
 * @description: TODO
 */

@RestController
public class TestController {
    public static final Logger logger = LoggerFactory.getLogger(TestController.class);

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @RequestMapping("/testSentinel")
    public void testSentinel() {
        stringRedisTemplate.opsForValue().set("name", "wyett");
        System.out.println(stringRedisTemplate.opsForValue().get("name"));
    }
}
