package com.shubnikofff.testtransactional.messaging;


import com.shubnikofff.testtransactional.dto.LikeRequest;
import com.shubnikofff.testtransactional.repository.AuthorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaConsumer {

    private final  AuthorRepository authorRepository;

    @KafkaListener(topics = "likes.request.v1", concurrency = "10")
    public void listen(LikeRequest request) {
        authorRepository.incrementLikesByName(request);
    }

}
