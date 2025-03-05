package com.shubnikofff.testtransactional.controller;


import com.shubnikofff.testtransactional.dto.LikeRequest;
import com.shubnikofff.testtransactional.messaging.KafkaProducer;
import com.shubnikofff.testtransactional.repository.AuthorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthorController {

    private final AuthorRepository authorRepository;
    private final KafkaProducer kafkaProducer;

    @GetMapping
    public Integer getAuthor() {
          return authorRepository.getLikesByName("Bruce Wayne");
    }

    @PostMapping("like")
    public void createLike(@RequestBody LikeRequest request) {
        kafkaProducer.sendLikeRequest(request);
//      authorRepository.incrementLikesByName(request);
    }

}
