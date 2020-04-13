package com.wyett.confirmlistener;

import com.rabbitmq.client.ConfirmListener;

import java.io.IOException;

/**
 * @author : wyettLei
 * @date : Created in 2020/4/13 13:00
 * @description: TODO
 */

public class WyettConfirmListner implements ConfirmListener {
    /**
     * @param deliveryTag
     * @param multiple
     * @throws IOException
     * @description 正常处理ack
     */
    @Override
    public void handleAck(long deliveryTag, boolean multiple) throws IOException {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("currency time:" + System.currentTimeMillis() + " handled ACK " + deliveryTag);
    }

    /**
     * @param deliveryTag
     * @param multiple
     * @throws IOException
     * @depscription 异常处理nack
     */
    @Override
    public void handleNack(long deliveryTag, boolean multiple) throws IOException {
        System.out.println("handled NACK" + deliveryTag);
    }
}
