package com.wyett.ttl;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

/**
 * @author : wyettLei
 * @date : Created in 2020/4/15 10:44
 * @description: TODO
 */

public class TtlRabbitMQProducer {
    public static void main(String[] args) throws IOException, TimeoutException {
        /**
         * 1.创建工厂
         */
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("10.10.19.69");
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
         * 4.
         */
        String exchangeName = "wyett.ttl.exchange01";
        String exchangeType = "topic";
        String routingKey = "wyett.ttl.key01";
        String queueName = "wyett.ttl.queue01";

        channel.exchangeDeclare(exchangeName, exchangeType, true, false, false, null);

        Map<String, Object> queueArgs = new HashMap<>();
        queueArgs.put("x-message-ttl", 10000);
        queueArgs.put("x-max-length", 4);
        // queue declare
        channel.queueDeclare(queueName, true,true,false, queueArgs);
        // bind
        channel.queueBind(queueName, exchangeName, routingKey);

        for (int i = 0; i < 10; i++) {
            channel.basicPublish(exchangeName, routingKey, null, ("hello, wyett" + i).getBytes());
        }





    }
}
