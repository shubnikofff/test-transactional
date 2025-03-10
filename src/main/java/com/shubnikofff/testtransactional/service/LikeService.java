package com.shubnikofff.testtransactional.service;


import com.shubnikofff.testtransactional.dto.LikeRequest;
import com.shubnikofff.testtransactional.repository.AuthorRepository;
import com.shubnikofff.testtransactional.repository.LikeBufferRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;


@Slf4j
@Service
@RequiredArgsConstructor
public class LikeService {

    private final AuthorRepository authorRepository;
    private final HistoryService historyService;
    private final LikeBufferRepository likeBufferRepository;

    //    @Transactional(isolation = Isolation.SERIALIZABLE)
    @Retryable
    public void addLikes(LikeRequest request) {
        authorRepository.getLikesByName(request.authorName()).ifPresentOrElse(
            likes -> {
                historyService.addToHistory(request, "RECEIVED");
                authorRepository.updateLikesByName(
                    likes + request.amount(),
                    request.authorName()
                );
            },
            () -> historyService.addToHistory(request, "ORPHANED")
        );
    }

    @Recover
//    @Transactional(isolation = Isolation.REPEATABLE_READ)
    void recoverAddLikes(Throwable ex, LikeRequest request) {
        log.warn("Recovering...");
        authorRepository.getLikesByName(request.authorName()).ifPresentOrElse(
            likes -> {
                authorRepository.updateLikesByName(
                    likes + request.amount(),
                    request.authorName()
                );
            },
            () -> historyService.addToHistory(request, "ORPHANED")
        );
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public void addLikesOptimistic(LikeRequest request) {
        int updateCount = 0;
        while (updateCount == 0) {
            final var author = authorRepository.getAuthorByName(request.authorName());
            updateCount = authorRepository.updateLikesByNameAndUpdatedAt(
                author.likes() + request.amount(),
                author.name(),
                author.updatedAt()
            );
        }

        historyService.addToHistory(request, "PROCESSED");
    }

    public void addLikesBuffered(LikeRequest request) {
        likeBufferRepository.addLikesByAuthor(request.authorName(), request.amount());
        historyService.addToHistory(request, "PROCESSED");
    }
}
