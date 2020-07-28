package com.wyett.rabbitmqwithspring.processor;

import org.springframework.core.annotation.Order;

import java.io.File;
import java.util.Map;

/**
 * @author : wyettLei
 * @date : Created in 2020/4/15 17:46
 * @description: TODO
 */

public class MyProcessor {
    public void handleMessage(String msgBody) {
        System.out.println("handleMessage "+msgBody);
    }

    public void consumerMsg(String msg){
        System.out.println("consumerMsg " + msg);
    }

    public void consumeTopicQueue(String msgBody) {
        System.out.println("consumerTopicQueue " + msgBody);

    }

    /**
     * 处理json
     * @param jsonMap
     */
    public void consumeJsonMessage(Map jsonMap) {
        System.out.println("处理json"+jsonMap);
    }

    /**
     * 处理order得
     * @param order
     */
    public void consumeJavaObjMessage(Order order) {
        System.out.println("处理java对象"+order.toString());

    }



    public void consumeFileMessage(File file) {
        System.out.println("处理文件"+file.getName());
    }
}
