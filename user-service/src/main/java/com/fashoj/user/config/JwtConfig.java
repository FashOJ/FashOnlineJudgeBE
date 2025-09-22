package com.fashoj.user.config;

import com.fashoj.user.util.JwtUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * JWT配置类
 * 
 * @author Lingbou
 * @version 1.0
 * @since 2025-09-22
 */
@Configuration
public class JwtConfig {

    /**
     * JWT工具类Bean
     */
    @Bean
    public JwtUtil jwtUtil() {
        return new JwtUtil();
    }
}