package com.wyett.confirmlistener;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author : wyettLei
 * @date : Created in 2020/4/13 12:11
 * @description: TODO
 */

public class ConfirmRabbitMQConsumer {
    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
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
         * 4.声明exchange
         */
        String exchangeName = "wyett.confirm.exchange01";
        String exchangeType = "topic";
        channel.exchangeDeclare(exchangeName, exchangeType, true, false, false, null);

        /**
         * 5.声明queue
         */
        String queueName = "wyett.confirm.queue01";
        channel.queueDeclare(queueName, true, false, false, null);


        /**
         * 6.绑定exchange和queue
         */
        String routingKey = "wyett.confirm.key0.#";
        channel.queueBind(queueName, exchangeName, routingKey);

        /**
         * 7.接收消息
         */
        String msg_prev = "hello, wyett";
//        channel.basicConsume(queueName, false, new ConfirmConsumer(channel));

        QueueingConsumer consumer = new QueueingConsumer(channel);
        channel.basicConsume(queueName, false, consumer);
        while (true) {
            QueueingConsumer.Delivery delivery = consumer.nextDelivery();
            System.out.println("消费" + new String(delivery.getBody()));
        }

    }
}
