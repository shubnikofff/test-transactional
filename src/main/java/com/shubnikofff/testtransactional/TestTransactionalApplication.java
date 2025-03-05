package com.shubnikofff.testtransactional;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.retry.annotation.EnableRetry;

@SpringBootApplication
@EnableRetry
public class TestTransactionalApplication {

    public static void main(String[] args) {
        SpringApplication.run(TestTransactionalApplication.class, args);
    }

}
