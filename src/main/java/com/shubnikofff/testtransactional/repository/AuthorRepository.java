package com.shubnikofff.testtransactional.repository;


import com.shubnikofff.testtransactional.model.Author;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Map;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class AuthorRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;


    public Optional<Integer> getLikesByName(String name) {
        try {
            final var likes = jdbcTemplate.queryForObject(
                "SELECT likes FROM author WHERE name = :name",
                Map.of("name", name),
                (ResultSet rs, int rowNum) -> rs.getInt("likes")
            );
            return Optional.ofNullable(likes);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public Author getAuthorByName(String name) {
        return jdbcTemplate.queryForObject(
            "SELECT name, likes, updated_at FROM author WHERE name = :name",
            Map.of("name", name),
            (ResultSet rs, int rowNum) -> new Author(
                rs.getString("name"),
                rs.getInt("likes"),
                rs.getTimestamp("updated_at").toInstant()
            )
        );
    }

    public void updateLikesByName(int likes, String name) {
        jdbcTemplate.update(
            "UPDATE author SET likes=:likes  WHERE name = :name",
            Map.of("name", name, "likes", likes)
        );
    }

    public int updateLikesByNameAndUpdatedAt(int likes, String name, Instant updatedAt) {
        final var mapSqlParameterSource = new MapSqlParameterSource(Map.of("name", name, "likes", likes))
            .addValue("updated_at", Timestamp.from(updatedAt));

        return jdbcTemplate.update(
            "UPDATE author SET likes=:likes  WHERE name = :name AND updated_at = :updated_at",
            mapSqlParameterSource
        );
    }
}
