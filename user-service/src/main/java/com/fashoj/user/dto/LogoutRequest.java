package com.fashoj.user.dto;

/**
 * 登出请求DTO
 * 
 * @author Lingbou
 * @version 1.0
 * @since 2025-09-22
 */
public class LogoutRequest {

    private Boolean logoutAll = false; // 是否登出所有设备，默认false

    // 构造函数
    public LogoutRequest() {}

    public LogoutRequest(Boolean logoutAll) {
        this.logoutAll = logoutAll;
    }

    // Getters and Setters
    public Boolean getLogoutAll() {
        return logoutAll;
    }

    public void setLogoutAll(Boolean logoutAll) {
        this.logoutAll = logoutAll;
    }

    @Override
    public String toString() {
        return "LogoutRequest{" +
                "logoutAll=" + logoutAll +
                '}';
    }
}