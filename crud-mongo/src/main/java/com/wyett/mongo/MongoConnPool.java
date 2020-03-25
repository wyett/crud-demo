package com.wyett.mongo;

import com.mongodb.MongoClient;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : wyettLei
 * @date : Created in 2020/3/25 12:18
 * @description: TODO
 */

public class MongoConnPool {
    private static List<MongoClient> clientPool = new ArrayList<MongoClient>();
}
