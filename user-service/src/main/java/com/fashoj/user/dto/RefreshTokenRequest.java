package com.fashoj.user.dto;

import jakarta.validation.constraints.NotBlank;

/**
 * Token刷新请求DTO
 * 
 * @author Lingbou
 * @version 1.0
 * @since 2025-09-22
 */
public class RefreshTokenRequest {

    @NotBlank(message = "刷新Token不能为空")
    private String refreshToken;

    // 构造函数
    public RefreshTokenRequest() {}

    public RefreshTokenRequest(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    // Getters and Setters
    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    @Override
    public String toString() {
        return "RefreshTokenRequest{" +
                "refreshToken='[PROTECTED]'" +
                '}';
    }
}