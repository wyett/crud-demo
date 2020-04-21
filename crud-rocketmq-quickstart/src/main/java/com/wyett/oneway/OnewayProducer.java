package com.wyett.oneway;

import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;

/**
 * @author : wyettLei
 * @date : Created in 2020/4/21 15:03
 * @description: TODO
 */

public class OnewayProducer {
    public static void main(String[] args) throws MQClientException, RemotingException, InterruptedException {
        DefaultMQProducer defaultMQProducer = new DefaultMQProducer("wyett-oneway-producer-group");
        defaultMQProducer.setNamesrvAddr("192.168.1.101:9876;192.168.1.102:9876");
        defaultMQProducer.start();

        Message msg = null;
        for (int i = 0; i < 10; i++) {
            msg = new Message("wyett-oneway-topic",
                    "onewayTest", "wyett-producer-test", ("hello, wyett " + i).getBytes());
            defaultMQProducer.sendOneway(msg);
        }

        defaultMQProducer.shutdown();
    }
}
