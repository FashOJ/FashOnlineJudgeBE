package com.fashoj.user.dto;

import jakarta.validation.constraints.NotBlank;

/**
 * 用户登录请求DTO
 * 
 * @author Lingbou
 * @version 1.0
 * @since 2025-09-22
 */
public class LoginRequest {

    @NotBlank(message = "登录标识不能为空")
    private String loginId; // 用户名或邮箱

    @NotBlank(message = "密码不能为空")
    private String password;

    private Boolean rememberMe = false; // 记住我，默认false

    // 构造函数
    public LoginRequest() {}

    public LoginRequest(String loginId, String password) {
        this.loginId = loginId;
        this.password = password;
    }

    public LoginRequest(String loginId, String password, Boolean rememberMe) {
        this.loginId = loginId;
        this.password = password;
        this.rememberMe = rememberMe;
    }

    // Getters and Setters
    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getRememberMe() {
        return rememberMe;
    }

    public void setRememberMe(Boolean rememberMe) {
        this.rememberMe = rememberMe;
    }

    @Override
    public String toString() {
        return "LoginRequest{" +
                "loginId='" + loginId + '\'' +
                ", rememberMe=" + rememberMe +
                '}';
    }
}