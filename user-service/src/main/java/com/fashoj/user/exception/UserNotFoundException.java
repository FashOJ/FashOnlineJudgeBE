package com.fashoj.user.exception;

/**
 * 用户未找到异常
 * 当根据用户名或邮箱查找用户失败时抛出
 * 
 * @author Lingbou
 * @version 1.0
 * @since 2025-09-22
 */
public class UserNotFoundException extends AuthException {
    
    public UserNotFoundException(String message) {
        super(message, 404);
    }
    
    public UserNotFoundException(String username, String email) {
        super(String.format("用户未找到: 用户名=%s, 邮箱=%s", username, email), 404);
    }
    
    public static UserNotFoundException byUsername(String username) {
        return new UserNotFoundException("用户名 '" + username + "' 不存在");
    }
    
    public static UserNotFoundException byEmail(String email) {
        return new UserNotFoundException("邮箱 '" + email + "' 未注册");
    }
}