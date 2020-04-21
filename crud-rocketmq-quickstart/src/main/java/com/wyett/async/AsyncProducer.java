package com.wyett.async;

import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * @author : wyettLei
 * @date : Created in 2020/4/21 9:26
 * @description: TODO
 */

public class AsyncProducer {
    public static void main(String[] args) throws MQClientException, RemotingException, InterruptedException {
        DefaultMQProducer defaultMQProducer = new DefaultMQProducer("wyett-async-producer-group");
        defaultMQProducer.setNamesrvAddr("192.168.1.101:9876;192.168.1.102:9876");
        defaultMQProducer.start();

        int messageCount = 5;
        CountDownLatch countDownLatch = new CountDownLatch(messageCount);
        Message message = null;
        for (int i = 0; i < messageCount; i++) {
            int index = i;
            message = new Message("wyett-async-topic",
                    "asyncTest", "asyncTest01", "hello, wyett".getBytes());
            defaultMQProducer.send(message, new SendCallback() {
                @Override
                public void onSuccess(SendResult sendResult) {
                    countDownLatch.countDown();
                    System.out.println("Index " + index + " msgid " + sendResult.getMsgId());
                }

                @Override
                public void onException(Throwable e) {
                    countDownLatch.countDown();
                    System.out.println("Index " + index + " exception " + e);
                    e.printStackTrace();
                }
            });
        }

        /** 等待5s, countDownLatch可以用来模拟并发，同时打出链接*/
        countDownLatch.await(5, TimeUnit.SECONDS);
        defaultMQProducer.shutdown();
    }
}
