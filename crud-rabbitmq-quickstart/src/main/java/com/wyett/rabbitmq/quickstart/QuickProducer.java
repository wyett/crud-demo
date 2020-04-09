package com.wyett.rabbitmq.quickstart;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author : wyettLei
 * @date : Created in 2020/4/9 14:40
 * @description: 向空exchange中发送消息，routingkey与queue名相同
 */

public class QuickProducer {
    public static void main(String[] args) throws IOException, TimeoutException {
        // 1.创建链接工厂，配置
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("192.168.0.100");
        connectionFactory.setPort(5672);
        connectionFactory.setUsername("wyett");
        connectionFactory.setPassword("jumpjump");
        connectionFactory.setVirtualHost("/wyett-vhost-01");
        connectionFactory.setConnectionTimeout(5000);

        // 2.创建链接
        Connection connection = connectionFactory.newConnection();

        // 3.创建channel
        Channel channel = connection.createChannel();

        // 4.发送数据
        String message = "test-";
        for (int i = 0; i < 5; i++) {
            /*
             * The default exchange is implicitly bound to every queue, with a routing key equal to the queue name.
             * It is not possible to explicitly bind to, or unbind from the default exchange. It also cannot be deleted.
             * 说明:加入我们消息发送的时候没有指定具体的交换机的话，那么就会发送到rabbimtq指定默认的交换机上，
             * 那么该交换机就会去根据routing_key 查找对应的queueName 然后发送的该队列上.
             */
            channel.basicPublish("", "wyett-queue-01", null, (message + i).getBytes());
        }

        // 5.关闭channel/链接
        channel.close();
        connection.close();
    }
}
