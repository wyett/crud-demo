package com.wyett.customconsumer;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;

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
}
