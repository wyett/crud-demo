package com.wyett.rabbitmq.direct_exchange;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author : wyettLei
 * @date : Created in 2020/4/9 18:13
 * @description: TODO
 */

public class DirectRabbitMQConsumer {
    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        // 1.创建链接工厂
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("192.168.0.100");
        connectionFactory.setPort(5672);
        connectionFactory.setUsername("wyett");
        connectionFactory.setPassword("jumpjump");
        connectionFactory.setVirtualHost("/wyett-vhost-01");
        connectionFactory.setConnectionTimeout(5000);

        // 2.创建链接
        Connection conn = connectionFactory.newConnection();

        // 3.创建channel
        Channel channel = conn.createChannel();

        /**
         * 4.绑定消息队列和routing key
         */
        String routingKey = "wyett-key-01";
        String queueName = "wyett-queue-01";
        String exchangeName = "wyett-exchange-01";
        String exchangeType = "direct";

        /**
         * 5.声明一个交换机
         * exchange:交换机的名称
         * type:交换机的类型 常见的有direct,fanout,topic等
         * durable:设置是否持久化。durable设置为true时表示持久化，反之非持久化.持久化可以将交换器存入磁盘，在服务器重启的时候不会丢失相关信息
         * autodelete:设置是否自动删除。autoDelete设置为true时，则表示自动删除。自动删除的前提是至少有一个队列或者交换器与这个交换器绑定，之后，所有与这个交换器绑定的队列或者交换器都与此解绑。
         * 不能错误的理解—当与此交换器连接的客户端都断开连接时，RabbitMq会自动删除本交换器
         * arguments:其它一些结构化的参数，比如：alternate-exchange
         */
        channel.exchangeDeclare(exchangeName, exchangeType, true, false, null);

        // 6.声明队列
        channel.queueDeclare(queueName, true,false, false, null);

        // 7.绑定交换机和队列
        channel.queueBind(queueName, exchangeName, routingKey);

        // 8.创建一个消费者
        QueueingConsumer queueingConsumer = new QueueingConsumer(channel);

        // 9.开始消费
        channel.basicConsume(queueName, true, queueingConsumer);
        while (true) {
            QueueingConsumer.Delivery delivery = queueingConsumer.nextDelivery();
            System.out.println(new String(delivery.getBody()));
        }

    }
}
