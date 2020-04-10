package com.wyett.rabbitmq.topicexchange;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author : wyettLei
 * @date : Created in 2020/4/9 18:36
 * @description: TODO
 */

public class TopicRabbitMQProducer {
    public static void main(String[] args) throws IOException, TimeoutException {
        /**
         * 创建factory
         */
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("192.168.0.100");
        connectionFactory.setPort(5672);
        connectionFactory.setUsername("wyett");
        connectionFactory.setPassword("jumpjump");
        connectionFactory.setVirtualHost("/wyett-vhost-01");
        connectionFactory.setConnectionTimeout(5000);

        /**
         * 创建链接
         */
        Connection connection = connectionFactory.newConnection();

        /**
         * 创建channel
         */
        Channel channel = connection.createChannel();

        String exchangeName = "wyett-exchange-topic-01";
        String routingKey = "wyett-key-01.aaaa.bbbb";

        /**
         * 发送数据
         */
        channel.basicPublish(exchangeName, routingKey, null, "第一条消息".getBytes());
        channel.basicPublish(exchangeName, routingKey, null, "第二条消息".getBytes());
        channel.basicPublish(exchangeName, routingKey, null, "第三条消息".getBytes());
    }
}
