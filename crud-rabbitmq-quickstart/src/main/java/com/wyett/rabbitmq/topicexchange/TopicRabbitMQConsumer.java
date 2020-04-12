package com.wyett.rabbitmq.topicexchange;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author : wyettLei
 * @date : Created in 2020/4/9 18:43
 * @description: TODO
 */

public class TopicRabbitMQConsumer {
    public static void main(String[] args) throws InterruptedException, IOException, TimeoutException {
        /**
         * 创建工厂
         */
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("192.168.1.101");
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

        /**
         * 声明exchange
         */
        String exchangeName = "wyett-exchange-topic-01";
        String exchangeType = "topic";
        channel.exchangeDeclare(exchangeName, exchangeType, false, false, null);

        /**
         * 声明queue
         */
        String queueName = "wyett-queue-topic-01";
        channel.queueDeclare(queueName, true, false, false, null);

        /**
         * 绑定queue
         */
        //String routingKey = "wyett-key-01";
        //String routingKey = "wyett-key-01.*";
        String routingKey = "wyett-key-01.#";
        channel.queueBind(queueName, exchangeName, routingKey);

        /**
         * 创建consumer
         */
        QueueingConsumer consumer = new QueueingConsumer(channel);

        /**
         * 消费
         */
        channel.basicConsume(queueName, true, consumer);
        while (true) {
            QueueingConsumer.Delivery delivery = consumer.nextDelivery();
            System.out.println(new String(delivery.getBody()));
        }

    }
}
