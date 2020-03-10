package com.wyett.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Pipeline;

import java.util.Arrays;
import java.util.List;

/**
 * @author : wyettLei
 * @date : Created in 2020/3/10 17:08
 * @description: TODO
 */

public class JedisTest {
    public static void main(String[] args) {
        // jedis pool config
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxTotal(20);
        jedisPoolConfig.setMaxIdle(10);
        jedisPoolConfig.setMinIdle(5);

        JedisPool jedisPool = new JedisPool(jedisPoolConfig, "10.18.56.30", 6379, 3000, "12345678");

        Jedis jedis = null;

        try {
            // 从连接池jedisPool中获取一个链接执行命令
            jedis = jedisPool.getResource();

            // string
            //System.out.println(jedis.set("single", "wyett"));
            //System.out.println(jedis.get("single"));

            // pipeline
            /*Pipeline pl = jedis.pipelined();
            for (int i = 0; i < 10; i++) {
                pl.incr("pipelineKey");
                pl.set("wyett" + i, "wyett");
            }
            List<Object> results = pl.syncAndReturnAll();
            System.out.println(results);*/

            // lua
            // redis-cli --eval /tmp/test.lua, 10
            jedis.set("product_stock_10016", "15");  //初始化商品10016的库存
            String script = " local count = redis.call('get', KEYS[1]) " +
                    " local a = tonumber(count) " +
                    " local b = tonumber(ARGV[1]) " +
                    " if a >= b then " +
                    "   redis.call('set', KEYS[1], count-b) " +
                    //模拟语法报错回滚操作"   bb == 0 " +
                    "   return 1 " +
                    " end " +
                    " return 0 ";
            Object obj = jedis.eval(script, Arrays.asList("product_count_10016"), Arrays.asList("10"));
            System.out.println(obj);


        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }
}
