package com.fashoj.problem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * 题目服务启动类
 */
@SpringBootApplication
@ComponentScan(basePackages = {"com.fashoj.problem", "com.fashoj.common"})
public class ProblemServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProblemServiceApplication.class, args);
    }
}