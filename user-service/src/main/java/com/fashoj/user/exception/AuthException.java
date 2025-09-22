package com.fashoj.user.exception;

/**
 * 认证异常基类
 * 用于处理认证相关的业务异常
 * 
 * @author Lingbou
 * @version 1.0
 * @since 2025-09-22
 */
public class AuthException extends RuntimeException {
    
    private final int errorCode;
    
    public AuthException(String message) {
        super(message);
        this.errorCode = 400;
    }
    
    public AuthException(String message, int errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
    
    public AuthException(String message, Throwable cause) {
        super(message, cause);
        this.errorCode = 400;
    }
    
    public AuthException(String message, Throwable cause, int errorCode) {
        super(message, cause);
        this.errorCode = errorCode;
    }
    
    public int getErrorCode() {
        return errorCode;
    }
}