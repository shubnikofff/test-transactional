package com.shubnikofff.testtransactional.messaging;


import com.shubnikofff.testtransactional.dto.LikeRequest;
import com.shubnikofff.testtransactional.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class KafkaConsumer {

    private final LikeService likeService;

    @KafkaListener(topics = "likes.request.v1", concurrency = "10", containerFactory = "likeRequestListenerContainerFactory")
//    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public void listen(LikeRequest request) {
//        likeService.addLikes(request);
        likeService.addLikesOptimistic(request);
    }

}
