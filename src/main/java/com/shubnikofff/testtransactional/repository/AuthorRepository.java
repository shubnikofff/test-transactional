package com.shubnikofff.testtransactional.repository;


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

    private final static String SELECT_LIKES_BY_NAME = "SELECT likes FROM author WHERE name = :name";

    public Integer getLikesByName(String name) {
        return jdbcTemplate.queryForObject(
                SELECT_LIKES_BY_NAME,
                Map.of("name", name),
                (ResultSet rs, int rowNum) -> rs.getInt("likes")
        );
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    @Retryable(maxAttempts = 15)
    public int incrementLikesByName(String name) {
        final var likes = jdbcTemplate.queryForObject(
                "SELECT likes FROM author WHERE name = :name",
                Map.of("name", name),
                (ResultSet rs, int rowNum) -> rs.getInt("likes")
        );

        return jdbcTemplate.update(
                "UPDATE author SET likes=:likes  WHERE name = :name",
                Map.of("name", name, "likes", likes + 1)
        );
    }
}
