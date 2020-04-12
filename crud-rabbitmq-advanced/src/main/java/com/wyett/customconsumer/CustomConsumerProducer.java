package com.wyett.customconsumer;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author : wyettLei
 * @date : Created in 2020/4/10 17:49
 * @description: TODO
 */

public class CustomConsumerProducer {
    public static void main(String[] args) throws IOException, TimeoutException {
        // 1.创建链接工厂
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("192.168.1.101");
        connectionFactory.setPort(5672);
        connectionFactory.setUsername("wyett");
        connectionFactory.setPassword("jumpjump");
        connectionFactory.setVirtualHost("/wyett-vhost-02");
        connectionFactory.setConnectionTimeout(5000);

        // 2.创建链接
        Connection conn = connectionFactory.newConnection();

        // 3.创建channel
        Channel channel = conn.createChannel();

        // 4.指定exchange,exchange type, 消息体
        String exchangeName = "wyett.custom.exchange";
        String routingKey = "wyett.custom.key";
        String message_prev = "wyett.custom.message.";


        // 5.发送消息
        channel.basicPublish(exchangeName, routingKey, null, message_prev.getBytes());
    }
}
