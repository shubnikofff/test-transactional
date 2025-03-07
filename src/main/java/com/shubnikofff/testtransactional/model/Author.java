package com.shubnikofff.testtransactional.model;

import java.time.Instant;

public record Author(
    String name,
    int likes,
    Instant updatedAt
) {
}
