package com.project.hanfu.config;

import com.project.hanfu.util.SnowFlake;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SnowFlakeConfig {

    @Bean
    public SnowFlake snowFlake() {
        long workerId = 1L; // 可以根据实际情况设置
        long datacenterId = 1L; // 可以根据实际情况设置
        return new SnowFlake(workerId, datacenterId);
    }
}