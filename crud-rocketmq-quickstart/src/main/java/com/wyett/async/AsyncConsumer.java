package com.wyett.async;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.List;

/**
 * @author : wyettLei
 * @date : Created in 2020/4/20 19:19
 * @description: TODO
 */

public class AsyncConsumer {
    public static void main(String[] args) throws MQClientException {
        DefaultMQPushConsumer defaultMQPushConsumer = new DefaultMQPushConsumer("wyett-default-sync-consumer");
        defaultMQPushConsumer.setNamesrvAddr("192.168.1.101:9876;192.168.1.102:9876");

        defaultMQPushConsumer.subscribe("wyett-async-topic", "*");
        defaultMQPushConsumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
                for (MessageExt msg: msgs) {
                    System.out.println(new String(msg.getBody()));
                }
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });

        defaultMQPushConsumer.start();
        System.out.println("consumer started");
    }
}
