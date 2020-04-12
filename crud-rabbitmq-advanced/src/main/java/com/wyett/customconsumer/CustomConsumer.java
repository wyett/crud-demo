package com.wyett.customconsumer;

import com.rabbitmq.client.*;

import java.io.IOException;

/**
 * @author : wyettLei
 * @date : Created in 2020/4/10 18:00
 * @description: TODO
 */

public class CustomConsumer extends DefaultConsumer {

    /**
     * Constructs a new instance and records its association to the passed-in channel.
     *
     * @param channel the channel to which this consumer is attached
     */
    public CustomConsumer(Channel channel) {
        super(channel);
    }

    public void handleDelivery(String consumerTag,
                               Envelope envelope,
                               AMQP.BasicProperties properties,
                               byte[] body)
            throws IOException {
        System.out.println(consumerTag);
        System.out.println(envelope.getDeliveryTag());
        System.out.println(envelope.getExchange());
        System.out.println(envelope.getRoutingKey());
        System.out.println(envelope.isRedeliver());
        System.out.println(envelope.toString());
        System.out.println(properties.toString());
        System.out.println(new String(body));
    }
}
