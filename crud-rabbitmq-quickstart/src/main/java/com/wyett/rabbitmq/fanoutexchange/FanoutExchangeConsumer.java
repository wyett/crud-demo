package com.wyett.rabbitmq.fanoutexchange;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.Queue;
import java.util.concurrent.TimeoutException;

/**
 * @author : wyettLei
 * @date : Created in 2020/4/10 9:20
 * @description: TODO
 */

public class FanoutExchangeConsumer {
    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        /**
         * 1. 创建链接工厂
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
         * 4.声明exchange
         */
        String exchangeName = "wyett-exchange-fanout-01";
        String exchangeType = "fanout";
        channel.exchangeDeclare(exchangeName, exchangeType, true, false, false, null);

        /**
         * 5.声明queue
         */
        String queueName = "wyett-queue-fanout-01";
        channel.queueDeclare(queueName,true, false,false, null);

        /**
         * 6.绑定exchange,queue, routingkey
         */
        channel.queueBind(queueName, exchangeName, "abse");

        /**
         * 7.创建consumer
         */
        QueueingConsumer consumer = new QueueingConsumer(channel);

        /**
         * 8.开始消费
         */
        channel.basicConsume(queueName, true, consumer);

        while (true) {
            QueueingConsumer.Delivery delivery = consumer.nextDelivery();
            System.out.println(new String(delivery.getBody()));
        }

    }
}
