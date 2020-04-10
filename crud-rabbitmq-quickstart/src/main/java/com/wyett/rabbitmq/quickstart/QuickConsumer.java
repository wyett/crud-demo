package com.wyett.rabbitmq.quickstart;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author : wyettLei
 * @date : Created in 2020/4/9 15:16
 * @description: routingkey与queue名相同，可正常消费，使用了默认exchange
 */

public class QuickConsumer {
    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        // 1.创建链接工厂
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("192.168.0.100");
        connectionFactory.setPort(5672);
        connectionFactory.setUsername("wyett");
        connectionFactory.setPassword("jumpjump");
        connectionFactory.setVirtualHost("/wyett-vhost-01");
        connectionFactory.setConnectionTimeout(5000);

        // 创建链接
        Connection connection = connectionFactory.newConnection();

        // 创建channel
        Channel channel = connection.createChannel();

        // 声明队列名称
        String queueName = "wyett-queue-01";
        channel.queueDeclare(queueName, true, false, false, null);

        // 创建消费队列
        //String exchangeName = "wyett-exchange-01"
        //channel.exchangeDeclare(exchangeName, "direct", true);
        //String queueName = channel.queueDeclare().getQueue();
        //channel.queueBind(queueName, exchangeName, routingKey);

        // 创建消费队列
        //DefaultConsumer defaultConsumer = new DefaultConsumer(channel);
        //channel.basicConsume(queueName, true, defaultConsumer);

        // 创建消费队列
        QueueingConsumer queueingConsumer = new QueueingConsumer(channel);
        channel.basicConsume(queueName, true, queueingConsumer);

        while (true) {
            QueueingConsumer.Delivery delivery = queueingConsumer.nextDelivery();
            System.out.println(new String(delivery.getBody()));
        }


    }
}
