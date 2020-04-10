package com.wyett.rabbitmq.fanoutexchange;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author : wyettLei
 * @date : Created in 2020/4/10 9:20
 * @description: fanout模式下，消息通exchange到queue，不会经过routingKey，只需要exchange和queue绑定即可，因此速度最快
 */

public class FanoutExchangeProducer {
    public static void main(String[] args) throws IOException, TimeoutException {
        /**
         * 1.创建链接工厂
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
         * 4.exchange和routingkey
         */
        String exchangeName = "wyett-exchange-fanout-01";
        String routingKey_prev = "wyett-exchange-fanout-";
        String messageBody = "message-";

        /**
         * 发布消息, 每次的key都不同
         */
        for (int i = 0; i < 1000; i++) {
            channel.basicPublish(exchangeName, routingKey_prev + i, null, (messageBody + i).getBytes());
        }
    }
}
