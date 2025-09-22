package com.fashoj.user.dto;

import jakarta.validation.constraints.*;

/**
 * 重置密码请求DTO
 * 
 * @author Lingbou
 * @version 1.0
 * @since 2025-09-22
 */
public class ResetPasswordRequest {

    @NotBlank(message = "邮箱不能为空")
    @Email(message = "邮箱格式不正确")
    private String email;

    @NotBlank(message = "重置验证码不能为空")
    @Pattern(regexp = "^\\d{6}$", message = "重置验证码必须是6位数字")
    private String resetCode;

    @NotBlank(message = "新密码不能为空")
    @Size(min = 6, max = 50, message = "密码长度必须在6-50个字符之间")
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d).*$", message = "密码必须包含至少一个字母和一个数字")
    private String newPassword;

    @NotBlank(message = "确认密码不能为空")
    private String confirmPassword;

    // 构造函数
    public ResetPasswordRequest() {}

    public ResetPasswordRequest(String email, String resetCode, String newPassword, String confirmPassword) {
        this.email = email;
        this.resetCode = resetCode;
        this.newPassword = newPassword;
        this.confirmPassword = confirmPassword;
    }

    // Getters and Setters
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getResetCode() {
        return resetCode;
    }

    public void setResetCode(String resetCode) {
        this.resetCode = resetCode;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    @Override
    public String toString() {
        return "ResetPasswordRequest{" +
                "email='" + email + '\'' +
                ", resetCode='[PROTECTED]'" +
                '}';
    }
}