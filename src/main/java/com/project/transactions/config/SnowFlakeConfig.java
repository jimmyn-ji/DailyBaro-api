package com.project.transactions.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.project.transactions.util.SnowFlake;

@Configuration
public class SnowFlakeConfig {

    @Bean
    public SnowFlake snowFlake() {
        long workerId = 1L;
        long datacenterId = 1L;
        return new SnowFlake(workerId, datacenterId);
    }
}