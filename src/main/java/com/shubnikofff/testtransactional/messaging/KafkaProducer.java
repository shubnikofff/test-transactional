package com.shubnikofff.testtransactional.messaging;


import com.shubnikofff.testtransactional.dto.LikeRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaProducer {

    private final KafkaTemplate<String, LikeRequest> kafkaTemplate;

    public void sendLikeRequest(LikeRequest request) {
        kafkaTemplate.send("likes.request.v1", request);
    }

}
