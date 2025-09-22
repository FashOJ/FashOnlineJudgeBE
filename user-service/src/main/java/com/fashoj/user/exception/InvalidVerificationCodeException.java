package com.fashoj.user.exception;

/**
 * 验证码无效异常
 * 当邮箱验证码或密码重置验证码无效时抛出
 * 
 * @author Lingbou
 * @version 1.0
 * @since 2025-09-22
 */
public class InvalidVerificationCodeException extends AuthException {
    
    public InvalidVerificationCodeException() {
        super("验证码无效或已过期", 400);
    }
    
    public InvalidVerificationCodeException(String message) {
        super(message, 400);
    }
    
    public static InvalidVerificationCodeException expired() {
        return new InvalidVerificationCodeException("验证码已过期");
    }
    
    public static InvalidVerificationCodeException wrong() {
        return new InvalidVerificationCodeException("验证码错误");
    }
    
    public static InvalidVerificationCodeException notFound() {
        return new InvalidVerificationCodeException("验证码不存在或已过期");
    }
    
    public static InvalidVerificationCodeException forEmail() {
        return new InvalidVerificationCodeException("邮箱验证码无效");
    }
    
    public static InvalidVerificationCodeException forPasswordReset() {
        return new InvalidVerificationCodeException("密码重置验证码无效");
    }
}