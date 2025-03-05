package com.shubnikofff.testtransactional.dto;

public record LikeRequest(
        String authorName,
        String userName,
        int amount
) {
}
