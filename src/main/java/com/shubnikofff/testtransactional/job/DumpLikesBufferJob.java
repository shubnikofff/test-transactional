package com.shubnikofff.testtransactional.job;


import com.shubnikofff.testtransactional.repository.AuthorRepository;
import com.shubnikofff.testtransactional.repository.LikeBufferRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


@Slf4j
@Component
@RequiredArgsConstructor
public class DumpLikesBufferJob {

    private final LikeBufferRepository likeBufferRepository;
    private final AuthorRepository authorRepository;

    @Scheduled(fixedRate = 1000)
    void run() {
        for (final var name: likeBufferRepository.getAllAuthorNames()) {
            var likes = likeBufferRepository.getLikesByAuthor(name);

            if(likes != null && likes > 0) {
                log.info("Dumping {} likes for {}", likes, name);
                authorRepository.getLikesByName(name).ifPresentOrElse(
                    currentLikes -> {
                        authorRepository.updateLikesByName(currentLikes + likes, name);
                        likeBufferRepository.deductLikesByAuthor(name, likes);
                    }, () -> {
                        throw new RuntimeException("Could not find author " + name);
                    });
            }
        }
    }

}
