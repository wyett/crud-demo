package com.wyett.redis.lock;

import org.redisson.Redisson;
import org.redisson.config.Config;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author : wyettLei
 * @date : Created in 2020/3/31 16:12
 * @description: TODO
 */

@SpringBootApplication
public class RedisLockApplication {
    public static void main(String[] args) {
        SpringApplication.run(RedisLockApplication.class, args);

    }

    /*
    public Redisson redisson() {
        Config config = new Config();
        // single
        config.useSingleServer().setAddress("redis://xxxxxxxxxxx:6379").setDatabase(0);

        // cluster
        config.useClusterServers()
                .addNodeAddress("redis://xxxxxxxxxx:6379")
                .addNodeAddress("redis://xxxxxxxxxx:6379")
                .addNodeAddress("redis://xxxxxxxxxx:6379");

        return (Redisson) Redisson.create(config);


    }
    */
}
