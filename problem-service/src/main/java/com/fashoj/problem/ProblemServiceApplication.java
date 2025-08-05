package com.fashoj.problem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * 题目服务启动类，提供题目管理和查询功能
 * 
 * @author Lingbou
 * @version 1.0
 * @since 2025-08-06
 */
@SpringBootApplication
@ComponentScan(basePackages = {"com.fashoj.problem", "com.fashoj.common"})
public class ProblemServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProblemServiceApplication.class, args);
    }
}