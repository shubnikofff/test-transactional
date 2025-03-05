package com.shubnikofff.testtransactional.repository;


import com.shubnikofff.testtransactional.dto.LikeRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class HistoryRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public void save(LikeRequest request) {
        jdbcTemplate.update(
                "insert into history(user_name, author_name, like_amount, created_at) values(:user_name, :author_name, :like_amount, :created_at)",
                Map.of(
                        "user_name", request.userName(),
                        "author_name", request.authorName(),
                        "like_amount", request.amount(),
                        "created_at", Timestamp.from(Instant.now())
                )
        );
    }
}
