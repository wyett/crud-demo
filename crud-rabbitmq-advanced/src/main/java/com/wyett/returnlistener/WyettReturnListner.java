package com.wyett.returnlistener;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.ReturnListener;

import java.io.IOException;

/**
 * @author : wyettLei
 * @date : Created in 2020/4/13 16:29
 * @description: TODO
 */

public class WyettReturnListner implements ReturnListener {
    @Override
    public void handleReturn(int replyCode, String replyText, String exchange, String routingKey, AMQP.BasicProperties properties, byte[] body) throws IOException {
        System.out.println(replyCode);
        System.out.println(replyText);
        System.out.println(exchange);
        System.out.println(routingKey);
        System.out.println(properties.toString());
        System.out.println(new String(body));
    }
}
