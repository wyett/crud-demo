package com.wyett.limitconsumer;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author : wyettLei
 * @date : Created in 2020/4/13 14:53
 * @description: limit consume
 */

public class QosRabbitProducer {
    public static void main(String[] args) throws IOException, TimeoutException {
        /**
         * 1.创建工厂
         */
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("192.168.1.101");
        connectionFactory.setPort(5672);
        connectionFactory.setUsername("wyett");
        connectionFactory.setPassword("jumpjump");
        connectionFactory.setVirtualHost("/wyett-vhost-02");
        connectionFactory.setConnectionTimeout(5000);

        /**
         * 2.创建链接
         */
        Connection conn = connectionFactory.newConnection();

        /**
         * 3.创建channel
         */
        Channel channel = conn.createChannel();

        /**
         * 4.声明exchangeName, queueName
         */
        String exchangeName = "wyett.qos.exchange01";
        String routingKey = "wyett.qos.key01";
        String msg_prev = "hello, wyett";

        /**
         * 5.发送消息
         */
        for (int i = 0; i < 100; i++) {
            channel.basicPublish(exchangeName, routingKey, null, (msg_prev + i).getBytes());
        }
    }
}
