package com.wyett.dlx;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.TimeoutException;

/**
 * @author : wyettLei
 * @date : Created in 2020/4/14 9:55
 * @description: TODO
 */

public class DlxRabbitMQProducer {
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
         * set properties
         */
        AMQP.BasicProperties basicProps = new AMQP.BasicProperties().builder()
                .deliveryMode(2)
                .contentType("utf8")
                .correlationId(UUID.randomUUID().toString())
                .expiration("10000")
                .build();

        /**
         * 5.发布消息
         */
        String exchangeName = "wyett.dlx.exchange01";
        String routingKey = "wyett.dlx.key";
        String msg_prev = "test";

        for (int i = 0; i < 100; i++) {
            channel.basicPublish(exchangeName, routingKey, basicProps, (msg_prev + i).getBytes());
        }
    }
}
