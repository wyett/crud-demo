package com.wyett.mongo;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;

/**
 * @author : wyettLei
 * @date : Created in 2020/3/25 11:47
 * @description: TODO
 */

public class MongoReplsetClientUrl {
    public static void main(String[] args) {
        // mongo connection url
        MongoClientURI mongoClientURI =
                new MongoClientURI("mongodb://wyett_rw:jumpjump@192.168.0.100:10000,10.10.18" +
                        ".240:10000/wyett?readPreference=secondaryPreferred");

        MongoClient client = new MongoClient(mongoClientURI);

        System.out.println(client.listDatabases());
        System.out.println(client.getDatabase("wyett"));
    }
}
