package com.wyett.acknackconsumer;

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
 * @date : Created in 2020/4/13 10:34
 * @description: TODO
 */

public class AckNackRabbitMQProducer {
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

        // 2.创建链接
        Connection conn = connectionFactory.newConnection();

        // 3.创建channel
        Channel channel = conn.createChannel();

        /**
         * 4.exchange和routingkey
         */
        String exchangeName = "wyett.acknack.exchange01";
        String exchangeType = "direct";

        String routingKey = "wyett.acknack.key01";
        String msg_prev = "wyett.acknack.prev";

        /**
         * 5.发送消息
         */
        Map<String, Object> headerMap = new HashMap<>();
        AMQP.BasicProperties basicProps = null;
        for (int i = 0; i < 10; i ++) {
            headerMap.put("mark", i);

            basicProps = new AMQP.BasicProperties().builder()
                    .deliveryMode(2)
                    .contentEncoding("utf-8")
                    .correlationId(UUID.randomUUID().toString())
                    .headers(headerMap)
                    .build();

            channel.basicPublish(exchangeName, routingKey, basicProps, (msg_prev + i).getBytes());

        }

        /**
         * 6.关闭
         */
        //channel.close();
        //conn.close();
    }
}
