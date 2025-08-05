package com.fashoj.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

/**
 * Gateway服务启动类，提供API网关功能和路由配置
 * 
 * @author Lingbou
 * @version 1.0
 * @since 2025-08-06
 */
@SpringBootApplication
public class GatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                // 用户服务路由
                .route("user-service", r -> r.path("/api/users/**")
                        .uri("http://localhost:8081"))
                // 题目服务路由
                .route("problem-service", r -> r.path("/api/problems/**")
                        .uri("http://localhost:8082"))
                // 提交服务路由
                .route("submission-service", r -> r.path("/api/submissions/**")
                        .uri("http://localhost:8083"))
                // 判题服务路由
                .route("judge-service", r -> r.path("/api/judge/**")
                        .uri("http://localhost:8084"))
                .build();
    }
}