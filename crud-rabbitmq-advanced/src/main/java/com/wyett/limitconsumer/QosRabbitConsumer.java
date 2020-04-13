package com.wyett.limitconsumer;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author : wyettLei
 * @date : Created in 2020/4/13 14:58
 * @description: 防止生产者发送大量消息到消费者，把消费者打垮
 */

public class QosRabbitConsumer {
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
        String exchangeName = "wyett.qos.exchange01";
        String exchangeType = "topic";
        channel.exchangeDeclare(exchangeName, exchangeType, true, false, false, null);

        /**
         * 5.声明queue
         */
        String queueName = "wyett.qos.queue01";
        channel.queueDeclare(queueName, true, false, false, null);

        /**
         * 6.绑定exchange, queue, routingkey
         */
        String routingKey = "wyett.qos.key01";
        channel.queueBind(queueName, exchangeName, routingKey);

        /**
         * 7.限流设置
         * prefetchSize: 一次消费的消息大小, 0表示不限制
         * prefetchCount: 一次消费几条消息, 0表示不限制
         * global: true:规则应用到channel, false表示规则应用到consumer
         */
        channel.basicQos(0, 2, false);

        /**
         * 8.接收消息
         */
        channel.basicConsume(queueName, false, new QosConsumer(channel));
    }
}
