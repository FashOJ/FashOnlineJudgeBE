package com.fashoj.user.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * 密码工具类
 * 提供密码加密和验证功能
 * 
 * @author Lingbou
 * @version 1.0
 * @since 2025-09-22
 */
@Component
public class PasswordUtil {

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * 加密密码
     * 
     * @param rawPassword 原始密码
     * @return 加密后的密码
     */
    public String encodePassword(String rawPassword) {
        return passwordEncoder.encode(rawPassword);
    }

    /**
     * 验证密码
     * 
     * @param rawPassword 原始密码
     * @param encodedPassword 加密后的密码
     * @return 密码是否匹配
     */
    public boolean matches(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

    /**
     * 生成随机密码
     * 
     * @param length 密码长度
     * @return 随机密码
     */
    public String generateRandomPassword(int length) {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder password = new StringBuilder();
        
        for (int i = 0; i < length; i++) {
            int index = (int) (Math.random() * chars.length());
            password.append(chars.charAt(index));
        }
        
        return password.toString();
    }

    /**
     * 验证密码强度
     * 
     * @param password 密码
     * @return 是否符合强度要求
     */
    public boolean isPasswordStrong(String password) {
        if (password == null || password.length() < 6 || password.length() > 50) {
            return false;
        }
        
        // 检查是否包含字母和数字
        boolean hasLetter = password.matches(".*[a-zA-Z].*");
        boolean hasDigit = password.matches(".*\\d.*");
        
        return hasLetter && hasDigit;
    }
}