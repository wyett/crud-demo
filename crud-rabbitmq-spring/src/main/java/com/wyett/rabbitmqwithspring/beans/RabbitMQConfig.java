package com.wyett.rabbitmqwithspring.beans;

import com.wyett.rabbitmqwithspring.processor.MyProcessor;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.context.annotation.Bean;

/**
 * @author : wyettLei
 * @date : Created in 2020/4/15 16:26
 * @description: TODO
 */

public class RabbitMQConfig {

    /**
     * connection
     */
    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory cachingConnectionFactory = new CachingConnectionFactory();
        cachingConnectionFactory.setHost("192.168.1.101");
        cachingConnectionFactory.setPort(5672);
        cachingConnectionFactory.setUsername("wyett");
        cachingConnectionFactory.setPassword("jumpjump");
        cachingConnectionFactory.setVirtualHost("/wyett-vhost-03");
        cachingConnectionFactory.setConnectionTimeout(5000);
        cachingConnectionFactory.setCloseTimeout(5000);
        return cachingConnectionFactory;
    }

    @Bean
    public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
        RabbitAdmin rabbitAdmin = new RabbitAdmin(connectionFactory);
        rabbitAdmin.setAutoStartup(true);
        return rabbitAdmin;
    }

    /**
     * 声明exchange
     */
    @Bean
    public TopicExchange topicExchange() {
        TopicExchange topicExchange = new TopicExchange("wyett.topic.exchange03", true, false);
        return topicExchange;
    }

    @Bean
    public DirectExchange directExchange() {
        DirectExchange directExchange = new DirectExchange("wyett.direct.exchange03", true, false);
        return directExchange;
    }

    @Bean
    public FanoutExchange fanoutExchange() {
        FanoutExchange fanoutExchange
                 = new FanoutExchange("wyett.fanout.exchange03", true, false);
        return fanoutExchange;
    }

    /**
     * 声明queue
     */
    @Bean
    public Queue getTopicQueue() {
        Queue queue = new Queue("wyett.topic.queue03", true, false, false, null);
        return queue;
    }

    @Bean
    public Queue getDirectQueue() {
        return new Queue("wyett.direct.queue03", true, false, false, null);
    }

    @Bean
    public Queue getFanoutQueue() {
        return new Queue("wyett.fanout.queue03", true, false, false, null);
    }

    @Bean
    public Queue getFileQueue() {
        return new Queue("wyett.file.queue03", true, false, false, null);
    }

    @Bean
    public Queue getOrderQueue() {
        return new Queue("wyett.order.queue03", true, false, false, null);
    }

    @Bean
    public Queue getAddressQueue() {
        return new Queue("wyett.address.queue03", true, false, false, null);
    }

    /**
     * 绑定queue和exchange
     */
    @Bean
    public Binding bindingTopicQueue() {
        return BindingBuilder.bind(getTopicQueue()).to(topicExchange()).with("wyett.topic.key.#");
    }

    @Bean
    public Binding bindingDirectQueue() {
        return BindingBuilder.bind(getDirectQueue()).to(directExchange()).with("wyett.direct.key03");
    }

    @Bean
    public Binding bindingFanoutQueue() {
        return BindingBuilder.bind(getFanoutQueue()).to(directExchange()).with("wyett.fanout.key03");
    }

    @Bean
    public Binding bindingFileQueue() {
        return BindingBuilder.bind(getFileQueue()).to(directExchange()).with("wyett.file.key03");
    }

    @Bean
    public Binding bindingAddressQueue() {
        return BindingBuilder.bind(getAddressQueue()).to(directExchange()).with("wyett.address.key03");
    }

    @Bean
    public Binding bindingOrderQueue() {
        return BindingBuilder.bind(getOrderQueue()).to(directExchange()).with("wyett.order.key03");
    }

    /**
     * RabbitTemplate
     */
    @Bean
    public RabbitTemplate rabbitTemplate() {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory());
        rabbitTemplate.setReceiveTimeout(5000);
        return rabbitTemplate;
    }

    /**
     * 消息监听容器
     */
    @Bean
    public SimpleMessageListenerContainer simpleMessageListenerContainer() {
        //创建监听容器
        SimpleMessageListenerContainer simpleMessageListenerContainer =
                new SimpleMessageListenerContainer(connectionFactory());
        //设置监听哪些queue
        simpleMessageListenerContainer.setQueues(getTopicQueue(), getDirectQueue(), getFanoutQueue(), getFileQueue(),
                getOrderQueue(), getAddressQueue());
        //设置最小的consumer
        simpleMessageListenerContainer.setConcurrentConsumers(5);
        //最大consumer
        simpleMessageListenerContainer.setMaxConcurrentConsumers(10);
        //签收模式
        simpleMessageListenerContainer.setAcknowledgeMode(AcknowledgeMode.AUTO);
        //拒绝重新入队
        simpleMessageListenerContainer.setDefaultRequeueRejected(false);


        /**
         * 使用默认监听
         */
        MessageListenerAdapter messageListenerAdapter = new MessageListenerAdapter();
//        messageListenerAdapter.setDefaultListenerMethod(MessageListenerAdapter);
        simpleMessageListenerContainer.setMessageListener(messageListenerAdapter);

        return simpleMessageListenerContainer;
    }
}
