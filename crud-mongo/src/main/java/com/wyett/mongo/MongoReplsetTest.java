package com.wyett.mongo;

import com.mongodb.*;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : wyettLei
 * @date : Created in 2020/3/24 16:25
 * @description: TODO
 */

public class MongoReplsetTest {
    public static void main(String[] args) {
        // replset
        List<ServerAddress> servers = new ArrayList<ServerAddress>();
        servers.add(new ServerAddress("192.168.0.100", 10000));
        servers.add(new ServerAddress("10.10.18.240", 10000));

        // option
        MongoClientOptions mongoClientOptions = MongoClientOptions.builder()
                .readPreference(ReadPreference.secondaryPreferred())
                .maxConnectionIdleTime(1000)
                .connectionsPerHost(10)
                .build();

        // user info
        MongoCredential mongoCredential = MongoCredential.createCredential("wyett_rw", "wyett", "jumpjump".toCharArray());

        // create mongoclient
        MongoClient mongoClient = new MongoClient(servers, mongoCredential, mongoClientOptions);

        // get db
//        System.out.println(mongoClient.getDatabase("wyett"));
        MongoDatabase curdb = mongoClient.getDatabase("wyett");
        MongoCollection<Document> curColl = curdb.getCollection("usermessage");
        System.out.println(curColl);

    }
}
