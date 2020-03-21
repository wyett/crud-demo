package com.wyett.redis;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPoolConfig;

import java.util.HashSet;
import java.util.Set;

/**
 * @author : wyettLei
 * @date : Created in 2020/3/21 15:34
 * @description: TODO
 */

public class JedisClusterTest {
    public static void main(String[] args) {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(20);
        config.setMinIdle(5);
        config.setMaxIdle(10);

        Set<HostAndPort> jedisClusterNode = new HashSet<>();
        jedisClusterNode.add(new HostAndPort("10.19.58.18", 6379));
        jedisClusterNode.add(new HostAndPort("10.19.58.19", 6379));
        jedisClusterNode.add(new HostAndPort("10.19.58.20", 6379));

        JedisCluster jedisCluster = null;

        try {
            jedisCluster = new JedisCluster(jedisClusterNode, 6000, 6000, 10, "123456789", config);
            System.out.println(jedisCluster.set("cluster", "wyett"));
            System.out.println(jedisCluster.get("cluster"));

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (jedisCluster != null) {
                jedisCluster.close();
            }
        }

    }
}
