package com.shubnikofff.testtransactional.repository;


import com.shubnikofff.testtransactional.dto.LikeRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class AuthorRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final HistoryRepository historyRepository;

    private final static String SELECT_LIKES_BY_NAME = "SELECT likes FROM author WHERE name = :name";

    public Integer getLikesByName(String name) {
        return jdbcTemplate.queryForObject(
            SELECT_LIKES_BY_NAME,
            Map.of("name", name),
            (ResultSet rs, int rowNum) -> rs.getInt("likes")
        );
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    @Retryable(maxAttempts = 5)
    public void incrementLikesByName(LikeRequest request) {
        historyRepository.save(request);

        final var likes = jdbcTemplate.queryForObject(
            "SELECT likes FROM author WHERE name = :name",
            Map.of("name", request.authorName()),
            (ResultSet rs, int rowNum) -> rs.getInt("likes")
        );

        jdbcTemplate.update(
            "UPDATE author SET likes=:likes  WHERE name = :name",
            Map.of("name", request.authorName(), "likes", likes + request.amount())
        );
    }
}
