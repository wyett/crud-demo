package com.wyett.redis;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisSentinelPool;

import java.util.HashSet;
import java.util.Set;

/**
 * @author : wyettLei
 * @date : Created in 2020/3/12 17:46
 * @description: TODO
 */

public class JedisSentinelTest {
    public static void main(String[] args) {
        // jedis pool config
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxTotal(20);
        jedisPoolConfig.setMaxIdle(10);
        jedisPoolConfig.setMinIdle(5);

        String myMaster = "mymaster";

        Set<String> sentinelSet = new HashSet<String>();
        sentinelSet.add(new HostAndPort("xxxxxxxx", 26379).toString());
        sentinelSet.add(new HostAndPort("xxxxxxxx", 26379).toString());
        sentinelSet.add(new HostAndPort("xxxxxxxx", 26379).toString());

        // jedisSentinelPool并非与java与sentinel建立connection，而是通过jedisSentinelPool发现master节点，并与redis建立链接
        JedisSentinelPool jedisSentinelPool
                = new JedisSentinelPool(myMaster, sentinelSet, jedisPoolConfig, 3000, "123456789");

        Jedis jedis = null;

        try {
            jedis = jedisSentinelPool.getResource();

            jedis.set("computer:brand", "thinkpad");
            System.out.println(jedis.get("computer:brand"));
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }



    }

}
