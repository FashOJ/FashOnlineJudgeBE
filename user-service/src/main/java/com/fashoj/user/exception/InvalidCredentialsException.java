package com.fashoj.user.exception;

/**
 * 无效凭据异常
 * 当用户名或密码错误时抛出
 * 
 * @author Lingbou
 * @version 1.0
 * @since 2025-09-22
 */
public class InvalidCredentialsException extends AuthException {
    
    public InvalidCredentialsException() {
        super("用户名或密码错误", 401);
    }
    
    public InvalidCredentialsException(String message) {
        super(message, 401);
    }
    
    public static InvalidCredentialsException wrongPassword() {
        return new InvalidCredentialsException("密码错误");
    }
    
    public static InvalidCredentialsException wrongUsernameOrPassword() {
        return new InvalidCredentialsException("用户名或密码错误");
    }
}