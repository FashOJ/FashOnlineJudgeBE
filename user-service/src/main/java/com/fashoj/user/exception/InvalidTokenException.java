package com.fashoj.user.exception;

/**
 * Token无效异常
 * 当JWT Token无效、过期或被篡改时抛出
 * 
 * @author Lingbou
 * @version 1.0
 * @since 2025-09-22
 */
public class InvalidTokenException extends AuthException {
    
    public InvalidTokenException() {
        super("Token无效", 401);
    }
    
    public InvalidTokenException(String message) {
        super(message, 401);
    }
    
    public static InvalidTokenException expired() {
        return new InvalidTokenException("Token已过期");
    }
    
    public static InvalidTokenException malformed() {
        return new InvalidTokenException("Token格式错误");
    }
    
    public static InvalidTokenException signature() {
        return new InvalidTokenException("Token签名无效");
    }
    
    public static InvalidTokenException blacklisted() {
        return new InvalidTokenException("Token已被注销");
    }
    
    public static InvalidTokenException wrongType(String expectedType) {
        return new InvalidTokenException("Token类型错误，期望: " + expectedType);
    }
}