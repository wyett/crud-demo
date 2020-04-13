package com.wyett.confirmlistener;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeoutException;

/**
 * @author : wyettLei
 * @date : Created in 2020/4/13 12:12
 * @description: TODO
 */

public class ConfirmRabbitMQProducer {
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
        channel.confirmSelect();

        /**
         * 4.声明exchangeName, queueName
         */
        String exchangeName = "wyett.confirm.exchange01";
        String routingKey = "wyett.confirm.key01";
        String msg_prev = "hello, wyett";
        //String queueName = "wyett.confirm.queue01";

        /**
         * 5.设置消息头
         */
        Map<String, Object> headerMap = new HashMap<>();
        headerMap.put("name", "wyett");

        AMQP.BasicProperties basicProps = new AMQP.BasicProperties().builder()
                .deliveryMode(2)
                .contentEncoding("utf-8")
                .correlationId(UUID.randomUUID().toString())
                .timestamp(new Date())
                .build();

        /**
         * 6.发送消息
         */
        channel.addConfirmListener(new WyettConfirmListner());
        for (int i = 0; i < 100; i++) {
            channel.basicPublish(exchangeName, routingKey, basicProps, (msg_prev + i).getBytes());
        }

    }
}
