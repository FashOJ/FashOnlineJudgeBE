package com.fashoj.judge;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * 判题服务启动类
 */
@SpringBootApplication
@ComponentScan(basePackages = {"com.fashoj.judge", "com.fashoj.common"})
public class JudgeServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(JudgeServiceApplication.class, args);
    }
}