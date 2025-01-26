package com.project.hanfu;

import lombok.extern.slf4j.Slf4j;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * 项目启动类
 */
@Slf4j
@SpringBootApplication
@MapperScan("com.project.hanfu.mapper")
public class BackendApplication {

    public static void main(String[] args) {

        //SpringBoot 执行启动
        SpringApplication.run(BackendApplication.class, args);

        log.info("=====================项目后端启动成功============================");
    }

}
