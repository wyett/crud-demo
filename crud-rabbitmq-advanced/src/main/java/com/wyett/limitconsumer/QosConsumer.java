package com.wyett.limitconsumer;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * @author : wyettLei
 * @date : Created in 2020/4/13 15:18
 * @description: TODO
 */

public class QosConsumer extends DefaultConsumer {
    private Channel channel;
    /**
     * Constructs a new instance and records its association to the passed-in channel.
     *
     * @param channel the channel to which this consumer is attached
     */
    public QosConsumer(Channel channel) {
        super(channel);
        this.channel = channel;
    }

    @Override
    public void handleDelivery(String consumerTag,
                               Envelope envelope,
                               AMQP.BasicProperties properties,
                               byte[] body)
            throws IOException {

        System.out.println(consumerTag);
        System.out.println(envelope.getDeliveryTag());
        System.out.println(envelope.toString());
        System.out.println(new String(body));

        // 关闭,改为手动
        //channel.basicAck(envelope.getDeliveryTag(), false);

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
