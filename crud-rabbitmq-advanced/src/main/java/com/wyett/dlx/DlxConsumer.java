package com.wyett.dlx;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

import java.io.IOException;

/**
 * @author : wyettLei
 * @date : Created in 2020/4/14 12:02
 * @description: TODO
 */

public class DlxConsumer extends DefaultConsumer {
    private Channel channel;
    /**
     * Constructs a new instance and records its association to the passed-in channel.
     *
     * @param channel the channel to which this consumer is attached
     */
    public DlxConsumer(Channel channel) {
        super(channel);
        this.channel = channel;
    }

    @Override
    public void handleDelivery(String consumerTag,
                               Envelope envelope,
                               AMQP.BasicProperties properties,
                               byte[] body)
            throws IOException {

        System.out.println(new String(body));

        channel.basicNack(envelope.getDeliveryTag(), false, false);
    }

}
