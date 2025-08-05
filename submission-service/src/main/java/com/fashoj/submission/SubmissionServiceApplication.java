package com.fashoj.submission;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * 提交服务启动类，提供代码提交和记录管理功能
 * 
 * @author Lingbou
 * @version 1.0
 * @since 2025-08-06
 */
@SpringBootApplication
@ComponentScan(basePackages = {"com.fashoj.submission", "com.fashoj.common"})
public class SubmissionServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(SubmissionServiceApplication.class, args);
    }
}