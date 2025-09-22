package com.fashoj.user.exception;

/**
 * 用户已存在异常
 * 当注册时用户名或邮箱已被使用时抛出
 * 
 * @author Lingbou
 * @version 1.0
 * @since 2025-09-22
 */
public class UserAlreadyExistsException extends AuthException {
    
    public UserAlreadyExistsException(String message) {
        super(message, 409);
    }
    
    public static UserAlreadyExistsException byUsername(String username) {
        return new UserAlreadyExistsException("用户名 '" + username + "' 已被使用");
    }
    
    public static UserAlreadyExistsException byEmail(String email) {
        return new UserAlreadyExistsException("邮箱 '" + email + "' 已被注册");
    }
    
    public static UserAlreadyExistsException byUsernameAndEmail(String username, String email) {
        return new UserAlreadyExistsException("用户名 '" + username + "' 和邮箱 '" + email + "' 已被使用");
    }
}