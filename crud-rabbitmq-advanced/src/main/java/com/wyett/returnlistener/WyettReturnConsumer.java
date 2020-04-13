package com.wyett.returnlistener;

import com.rabbitmq.client.*;

import java.io.IOException;

/**
 * @author : wyettLei
 * @date : Created in 2020/4/13 16:53
 * @description: TODO
 */

public class WyettReturnConsumer extends DefaultConsumer {
    /**
     * Constructs a new instance and records its association to the passed-in channel.
     *
     * @param channel the channel to which this consumer is attached
     */
    public WyettReturnConsumer(Channel channel) {
        super(channel);
    }

    @Override
    public void handleDelivery(String consumerTag,
                               Envelope envelope,
                               AMQP.BasicProperties properties,
                               byte[] body)
            throws IOException
    {
        System.out.println(new String(body));
    }

}
