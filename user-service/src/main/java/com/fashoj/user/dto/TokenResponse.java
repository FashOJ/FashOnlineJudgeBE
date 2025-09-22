package com.fashoj.user.dto;

/**
 * Token响应DTO，用于刷新Token接口的响应
 * 
 * @author Lingbou
 * @version 1.0
 * @since 2025-09-22
 */
public class TokenResponse {

    private String accessToken;
    private String refreshToken;
    private Long expiresIn; // Token过期时间（秒）

    // 构造函数
    public TokenResponse() {}

    public TokenResponse(String accessToken, String refreshToken, Long expiresIn) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.expiresIn = expiresIn;
    }

    // Getters and Setters
    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public Long getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(Long expiresIn) {
        this.expiresIn = expiresIn;
    }
}