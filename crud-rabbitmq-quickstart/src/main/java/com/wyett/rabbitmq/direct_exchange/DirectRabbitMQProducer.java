package com.wyett.rabbitmq.direct_exchange;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author : wyettLei
 * @date : Created in 2020/4/9 18:00
 * @description: direct exchange
 */

public class DirectRabbitMQProducer {
    public static void main(String[] args) throws IOException, TimeoutException {
        // 1.创建链接工厂
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("192.168.0.100");
        connectionFactory.setPort(5672);
        connectionFactory.setUsername("wyett");
        connectionFactory.setPassword("jumpjump");
        connectionFactory.setVirtualHost("/wyett-vhost-01");
        connectionFactory.setConnectionTimeout(5000);

        // 2.创建链接
        Connection conn = connectionFactory.newConnection();

        // 3.创建channel
        Channel channel = conn.createChannel();

        // 4.指定exchange,exchange type, 消息体
        String exchangeName = "wyett-exchange-01";
        String routingKey = "wyett-key-01";
        String message_prev = "wyett_direct_";


        // 5.发送消息
        channel.basicPublish(exchangeName, routingKey, null, message_prev.getBytes());

    }
}
