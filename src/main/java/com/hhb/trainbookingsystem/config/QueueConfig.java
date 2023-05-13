package com.hhb.trainbookingsystem.config;

import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.jms.Queue;

@Configuration
public class QueueConfig {

    @Bean
    public Queue queue(){
        return new ActiveMQQueue("ActiveMQQueue");
    }
}
