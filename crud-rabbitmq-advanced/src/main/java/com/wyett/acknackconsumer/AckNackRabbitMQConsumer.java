package com.wyett.acknackconsumer;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author : wyettLei
 * @date : Created in 2020/4/13 10:48
 * @description: TODO
 */

public class AckNackRabbitMQConsumer {
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
        Connection connection = connectionFactory.newConnection();

        /**
         * 3.创建channel
         */
        Channel channel = connection.createChannel();

        /**
         * 4.声明exchange
         */
        String exchangeName = "wyett.acknack.exchange01";
        String exchangeType = "direct";
        channel.exchangeDeclare(exchangeName, exchangeType, true, false,false, null);

        /**
         * 5.声明queue
         */
        String queueName = "wyett.acknack.queue01";
        channel.queueDeclare(queueName, true, false, false, null);

        /**
         * 6.绑定exchange,queue
         */
        String routingKey = "wyett.acknack.key01";
        channel.queueBind(queueName, exchangeName, routingKey);

        /**
         * 消费端限流 需要关闭消息自动签收
         */
        channel.basicConsume(queueName, false, new AckNackConsumer(channel));
    }
}
