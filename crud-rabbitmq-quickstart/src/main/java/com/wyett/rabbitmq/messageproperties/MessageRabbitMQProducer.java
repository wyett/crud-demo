package com.wyett.rabbitmq.messageproperties;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeoutException;

/**
 * @author : wyettLei
 * @date : Created in 2020/4/10 9:59
 * @description: TODO
 */

public class MessageRabbitMQProducer {
    public static void main(String[] args) throws IOException, TimeoutException {
        /**
         * 1.声明连接池
         */
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("192.168.0.100");
        connectionFactory.setPort(5672);
        connectionFactory.setUsername("wyett");
        connectionFactory.setPassword("jumpjump");
        connectionFactory.setVirtualHost("/wyett-vhost-01");
        connectionFactory.setConnectionTimeout(5000);

        /**
         * 2.创建链接
         */
        Connection connection = connectionFactory.newConnection();

        /**
         * 3.创建channel
         */
        Channel channel = connection.createChannel();

        /**
         * 4.设置消息属性
         */
        String exchangeName = "wyett-msg-property-exchange-01";
        String routingKey = "wyett-msg-property-key-01";

        Map<String, Object> headMap = new HashMap<>();
        headMap.put("owner", "wyett");
        headMap.put("type", "test");

        AMQP.BasicProperties basicProperties = new AMQP.BasicProperties().builder()
                .deliveryMode(2)//2标识持久化消息  1标识 服务重启后 消息不会被持久化
                .expiration("10000")//消息过期 10s
                .contentEncoding("utf-8")
                .correlationId(UUID.randomUUID().toString())
                .headers(headMap)
                .build();

        /**
         * 5.发送消息
         */
        String msg_prev = "message-";
        try {
            for (int i = 0; i < 10; i++) {
                channel.basicPublish(exchangeName, routingKey, basicProperties, (msg_prev + i).getBytes());
            }
            channel.basicPublish(exchangeName, routingKey, basicProperties, "第二条消息".getBytes());
            channel.basicPublish(exchangeName, routingKey, basicProperties, "第三条消息".getBytes());
        } finally {
            /**
             * 6.关闭连接
             */
            channel.close();
            connection.close();
        }

    }
}
