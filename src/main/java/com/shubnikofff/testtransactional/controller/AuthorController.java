package com.shubnikofff.testtransactional.controller;


import com.shubnikofff.testtransactional.dto.LikeRequest;
import com.shubnikofff.testtransactional.messaging.KafkaProducer;
import com.shubnikofff.testtransactional.repository.LikeBufferRepository;
import com.shubnikofff.testtransactional.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthorController {

    private final KafkaProducer kafkaProducer;

    private final LikeBufferRepository likeBufferRepository;

    private final LikeService likeService;


    @PostMapping("like")
    public void createLike(@RequestBody LikeRequest request) {
//      authorRepository.incrementLikesByName(request);
        kafkaProducer.sendLikeRequest(request);
//        likeService.addLikesBuffered(request);
    }

}
