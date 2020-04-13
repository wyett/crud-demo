package com.wyett.acknackconsumer;

import com.rabbitmq.client.*;

import java.io.IOException;

/**
 * @author : wyettLei
 * @date : Created in 2020/4/13 10:57
 * @description: TODO
 */

public class AckNackConsumer extends DefaultConsumer {
    private Channel channel;

    /**
     * Constructs a new instance and records its association to the passed-in channel.
     *
     * @param channel the channel to which this consumer is attached
     */
    public AckNackConsumer(Channel channel) {
        super(channel);
        this.channel = channel;
    }

    @Override
    public void handleDelivery(String consumerTag,
                               Envelope envelope,
                               AMQP.BasicProperties properties,
                               byte[] body)
            throws IOException {
        Integer mark = (Integer) properties.getHeaders().get("mark");
        try {
            if (mark != 0) {
                System.out.println("已处理" + new String(body));
                channel.basicAck(envelope.getDeliveryTag(), false);
            } else {
                throw new RuntimeException("模拟异常业务");
            }
        } catch (Exception e) {
            System.out.println("异常消息" + new String(body));
            /**
             * deliverytag: 接收到的消息的唯一标识
             * multiple: 支持批量签收，批量拒绝
             * requeue: 是否重入队列
             */
            channel.basicNack(envelope.getDeliveryTag(), false, false);
//            channel.basicNack(envelope.getDeliveryTag(), false, true);
        }
    }
}
