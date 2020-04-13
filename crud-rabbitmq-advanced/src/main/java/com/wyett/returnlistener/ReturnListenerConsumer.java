package com.wyett.returnlistener;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.wyett.limitconsumer.QosConsumer;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author : wyettLei
 * @date : Created in 2020/4/13 15:43
 * @description: TODO
 */

public class ReturnListenerConsumer {
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
         * 4.声明exchange
         */
        String exchangeName = "wyett.returnlistener.exchange01";
        String exchangeType = "topic";
        channel.exchangeDeclare(exchangeName, exchangeType, true, false, false, null);

        /**
         * 5.声明queue
         */
        String queueName = "wyett.returnlistener.queue01";
        channel.queueDeclare(queueName, true, false, false, null);

        /**
         * 6.绑定exchange, queue, routingkey
         */
        String routingKey = "wyett.returnlistener.key01";
        channel.queueBind(queueName, exchangeName, routingKey);

        /**
         * 7.接收消息
         */
        channel.basicConsume(queueName, false, new WyettReturnConsumer(channel));
    }
}
