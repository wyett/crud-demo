package com.wyett.sync;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;

/**
 * @author : wyettLei
 * @date : Created in 2020/4/20 19:12
 * @description: TODO
 */

public class SyncProducer {
    public static void main(String[] args) throws MQClientException, RemotingException, InterruptedException, MQBrokerException {
        DefaultMQProducer defaultMQProducer = new DefaultMQProducer("wyett-default-producer-group");
        defaultMQProducer.setNamesrvAddr("192.168.1.101:9876;192.168.1.102:9876");
        defaultMQProducer.start();

        Message message = new Message("Test-sync-topic", "tagSync", "tag", "hello, wyett".getBytes());
        SendResult sendResult = defaultMQProducer.send(message);


        System.out.printf("%s%n", sendResult);
        defaultMQProducer.shutdown();
    }
}
