package com.fashoj.user.exception;

/**
 * 账户锁定异常
 * 当用户账户因多次登录失败被锁定时抛出
 * 
 * @author Lingbou
 * @version 1.0
 * @since 2025-09-22
 */
public class AccountLockedException extends AuthException {
    
    private final long lockTimeRemaining;
    
    public AccountLockedException(long lockTimeRemaining) {
        super("账户已被锁定，请 " + (lockTimeRemaining / 60) + " 分钟后重试", 423);
        this.lockTimeRemaining = lockTimeRemaining;
    }
    
    public AccountLockedException(String message, long lockTimeRemaining) {
        super(message, 423);
        this.lockTimeRemaining = lockTimeRemaining;
    }
    
    public long getLockTimeRemaining() {
        return lockTimeRemaining;
    }
    
    public static AccountLockedException withRemainingTime(long seconds) {
        return new AccountLockedException(seconds);
    }
}