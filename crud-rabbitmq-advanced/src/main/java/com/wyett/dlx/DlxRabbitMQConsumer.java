package com.wyett.dlx;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

/**
 * @author : wyettLei
 * @date : Created in 2020/4/14 9:55
 * @description: TODO
 */

public class DlxRabbitMQConsumer {
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
        String exchangeName = "wyett.dlx.exchange01";
        String exchangeType = "topic";
        String routingKey = "wyett.dlx.key.#";
        channel.exchangeDeclare(exchangeName, exchangeType, true, false, false, null);

        /**
         * 5.声明死信exchange
         */
        String dlxExchangeName = "wyett.dlx.exchange02";
        String dlxRoutingKey = "wyett.dlx.#";
        channel.exchangeDeclare(dlxExchangeName, exchangeType, true, false, false, null);

        /**
         * 6.声明queue
         */
        Map<String, Object> queueArgs = new HashMap<>();
        queueArgs.put("dlx.exchange.name", dlxExchangeName);
        queueArgs.put("dlx.queue.len", 4);

        String queueName = "wyett.dlx.queue01";
        channel.queueDeclare(queueName, true, false, false, queueArgs);

        /**
         * 7.绑定queue, exchange, routingkey
         */
        channel.queueBind(queueName, exchangeName, routingKey);

        /**
         * 8.声明死信queue
         */
        String dlxQueueName = "wyett.dlx.queue02";
        channel.queueDeclare(dlxQueueName, true, false, false, null);

        /**
         * 9.绑定死信queue, exchange
         */
        channel.queueBind(dlxQueueName, dlxExchangeName, dlxRoutingKey);

        /**
         * 10.执行queue
         */
        channel.basicConsume(queueName, false, new DlxConsumer(channel));



    }
}
