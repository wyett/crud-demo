package com.wyett.returnlistener;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.wyett.confirmlistener.WyettConfirmListner;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeoutException;

/**
 * @author : wyettLei
 * @date : Created in 2020/4/13 15:43
 * @description: TODO
 */

public class ReturnListenerProducer {
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
        channel.addConfirmListener(new WyettConfirmListner());
        channel.addReturnListener(new WyettReturnListner());

        /**
         * 4.声明exchangeName
         */
        String exchangeName = "wyett.returnlistener.exchange01";
        String routingKey = "wyett.returnlistener.key01";
        String errRoutingKey = "wyett.returnlistener.key02";

        /**
         * 5.设置属性
         */
        Map<String, Object> headerMap = new HashMap<>();
        headerMap.put("name", "wyett");
        headerMap.put("location", "BJ");

        AMQP.BasicProperties baseProps = new AMQP.BasicProperties().builder()
                .deliveryMode(2)
                .contentEncoding("utf-8")
                .correlationId(UUID.randomUUID().toString())
                .timestamp(new Date())
                .build();

        /**
         * 6.发送消息, 开启mandatory，配合returnListener
         */

        // msg, mandatory为false
        String msg = "hello, wyett" + System.currentTimeMillis();
//        channel.basicPublish(exchangeName, routingKey, null, msg.getBytes());
        // mandatory为true
        String okmsg = "hello, wyett, send to right routingKey" + System.currentTimeMillis();
//        channel.basicPublish(exchangeName, routingKey, true, null, msg.getBytes());
        // mandatory为true
        String errmsg = "hello, wyett,send to errRoutingKey" + System.currentTimeMillis();
        channel.basicPublish(exchangeName, errRoutingKey, true, null, msg.getBytes());
    }
}
