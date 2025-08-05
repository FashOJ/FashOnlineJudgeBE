package com.fashoj.judge;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * 判题服务启动类，提供代码编译和执行判题功能
 * 
 * @author Lingbou
 * @version 1.0
 * @since 2025-08-06
 */
@SpringBootApplication
@ComponentScan(basePackages = {"com.fashoj.judge", "com.fashoj.common"})
public class JudgeServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(JudgeServiceApplication.class, args);
    }
}