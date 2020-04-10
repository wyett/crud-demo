package com.wyett.rabbitmq.messageproperties;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author : wyettLei
 * @date : Created in 2020/4/10 9:59
 * @description: TODO
 */

public class MessageRabbitMQConsumer {
    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
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
         * 4.声明exchange
         */
        String exchangeName = "wyett-msg-property-exchange-01";
        String exchangeType = "topic";
        channel.exchangeDeclare(exchangeName, exchangeType, true, false, false, null);

        /**
         * 5.声明queue
         */
        String queueName = "wyett-msg-property-queue-01";
        channel.queueDeclare(queueName, true, false, false, null);

        /**
         * 6.绑定queue
         */
        String routingKey = "wyett-msg-property-key-01";
        channel.queueBind(queueName, exchangeName, routingKey);

        /**
         * 7.创建consumer
         */
        QueueingConsumer consumer = new QueueingConsumer(channel);

        /**
         * 8.消费
         */
        channel.basicConsume(queueName, true, consumer);
        while (true) {
            QueueingConsumer.Delivery delivery = consumer.nextDelivery();
            System.out.println(new String(delivery.getBody()));
            System.out.println("name:" + delivery.getProperties().getHeaders().get("name"));
            System.out.println("Encoding:" + delivery.getProperties().getContentEncoding());
            System.out.println("CorrelationId:" + delivery.getProperties().getCorrelationId());
        }

    }
}
