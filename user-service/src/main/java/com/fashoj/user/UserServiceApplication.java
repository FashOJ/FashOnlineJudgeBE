package com.fashoj.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * 用户服务启动类，提供用户管理和认证相关功能
 * 
 * @author Lingbou
 * @version 1.0
 * @since 2025-08-06
 */
@SpringBootApplication
@ComponentScan(basePackages = {"com.fashoj.user", "com.fashoj.common"})
public class UserServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserServiceApplication.class, args);
    }
}