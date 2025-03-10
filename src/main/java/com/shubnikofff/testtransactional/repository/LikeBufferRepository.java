package com.shubnikofff.testtransactional.repository;


import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.Set;


@Slf4j
@Repository
@RequiredArgsConstructor
public class LikeBufferRepository {

    private final RedisTemplate<String, Integer> redisTemplate;

    @PostConstruct
    public void clearRedisOnStartup() {
        redisTemplate.getConnectionFactory().getConnection().serverCommands().flushAll();
        log.info("✅ Redis data erased using RedisTemplate.");
    }

    public void addLikesByAuthor(String authorName, int amount) {
        redisTemplate.opsForValue().increment(authorName, amount);
    }

    public Integer getLikesByAuthor(String authorName) {
        return redisTemplate.opsForValue().get(authorName);
    }

    public void deductLikesByAuthor(String authorName, int amount) {
        redisTemplate.opsForValue().decrement(authorName, amount);
    }

    public Set<String> getAllAuthorNames() {
        return redisTemplate.keys("*");
    }

}
