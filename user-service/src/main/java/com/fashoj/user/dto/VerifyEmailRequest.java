package com.fashoj.user.dto;

import jakarta.validation.constraints.*;

/**
 * 邮箱验证请求DTO
 * 
 * @author Lingbou
 * @version 1.0
 * @since 2025-09-22
 */
public class VerifyEmailRequest {

    @NotBlank(message = "邮箱不能为空")
    @Email(message = "邮箱格式不正确")
    private String email;

    @NotBlank(message = "验证码不能为空")
    @Pattern(regexp = "^\\d{6}$", message = "验证码必须是6位数字")
    private String verificationCode;

    // 构造函数
    public VerifyEmailRequest() {}

    public VerifyEmailRequest(String email, String verificationCode) {
        this.email = email;
        this.verificationCode = verificationCode;
    }

    // Getters and Setters
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getVerificationCode() {
        return verificationCode;
    }

    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }

    @Override
    public String toString() {
        return "VerifyEmailRequest{" +
                "email='" + email + '\'' +
                ", verificationCode='[PROTECTED]'" +
                '}';
    }
}