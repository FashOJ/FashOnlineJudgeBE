package com.fashoj.user.dto;

/**
 * 认证响应DTO，用于返回登录成功后的信息
 * 
 * @author Lingbou
 * @version 1.0
 * @since 2025-09-22
 */
public class AuthResponse {

    private UserInfo user;
    private String accessToken;
    private String refreshToken;
    private Long expiresIn; // Token过期时间（秒）

    // 构造函数
    public AuthResponse() {}

    public AuthResponse(UserInfo user, String accessToken, String refreshToken, Long expiresIn) {
        this.user = user;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.expiresIn = expiresIn;
    }

    // Getters and Setters
    public UserInfo getUser() {
        return user;
    }

    public void setUser(UserInfo user) {
        this.user = user;
    }

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