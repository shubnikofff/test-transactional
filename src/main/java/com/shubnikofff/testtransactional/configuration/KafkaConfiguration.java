package com.shubnikofff.testtransactional.configuration;


import com.shubnikofff.testtransactional.dto.LikeRequest;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.util.backoff.FixedBackOff;


@Configuration
public class KafkaConfiguration {

    @Bean
    public NewTopic likeRequestTopic() {
        return TopicBuilder
                .name("likes.request.v1")
                .partitions(10)
                .build();
    }

    @Bean
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, LikeRequest>> likeRequestListenerContainerFactory(
        ConcurrentKafkaListenerContainerFactory<String, LikeRequest> factory
    ) {
        factory.setCommonErrorHandler(new DefaultErrorHandler(new FixedBackOff(0, 3)));
        return factory;
    }

}
