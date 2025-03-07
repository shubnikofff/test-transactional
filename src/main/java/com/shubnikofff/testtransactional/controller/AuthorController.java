package com.shubnikofff.testtransactional.controller;


import com.shubnikofff.testtransactional.dto.LikeRequest;
import com.shubnikofff.testtransactional.messaging.KafkaProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthorController {

    private final KafkaProducer kafkaProducer;


    @PostMapping("like")
    public void createLike(@RequestBody LikeRequest request) {
        kafkaProducer.sendLikeRequest(request);
//      authorRepository.incrementLikesByName(request);
    }

}
