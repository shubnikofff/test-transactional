package com.shubnikofff.testtransactional.configuration;


import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaConfiguration {

    @Bean
    public NewTopic likeRequestTopic() {
        return TopicBuilder
                .name("likes.request.v1")
                .partitions(10)
                .build();
    }

}
