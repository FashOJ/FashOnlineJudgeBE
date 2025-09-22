package com.fashoj.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * Redis缓存服务
 * 用于Token黑名单和验证码存储
 * 
 * @author Lingbou
 * @version 1.0
 * @since 2025-09-22
 */
@Service
public class RedisService {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    // 缓存键前缀
    private static final String TOKEN_BLACKLIST_PREFIX = "auth:blacklist:";
    private static final String EMAIL_VERIFICATION_PREFIX = "auth:email_verify:";
    private static final String PASSWORD_RESET_PREFIX = "auth:password_reset:";
    private static final String LOGIN_ATTEMPTS_PREFIX = "auth:login_attempts:";

    /**
     * 将Token加入黑名单
     * 
     * @param token Token
     * @param expireTime 过期时间（秒）
     */
    public void addTokenToBlacklist(String token, long expireTime) {
        String key = TOKEN_BLACKLIST_PREFIX + token;
        redisTemplate.opsForValue().set(key, "blacklisted", expireTime, TimeUnit.SECONDS);
    }

    /**
     * 检查Token是否在黑名单中
     * 
     * @param token Token
     * @return 是否在黑名单中
     */
    public boolean isTokenBlacklisted(String token) {
        String key = TOKEN_BLACKLIST_PREFIX + token;
        return redisTemplate.hasKey(key);
    }

    /**
     * 存储邮箱验证码
     * 
     * @param email 邮箱
     * @param code 验证码
     * @param expireMinutes 过期时间（分钟）
     */
    public void storeEmailVerificationCode(String email, String code, long expireMinutes) {
        String key = EMAIL_VERIFICATION_PREFIX + email;
        redisTemplate.opsForValue().set(key, code, expireMinutes, TimeUnit.MINUTES);
    }

    /**
     * 获取邮箱验证码
     * 
     * @param email 邮箱
     * @return 验证码
     */
    public String getEmailVerificationCode(String email) {
        String key = EMAIL_VERIFICATION_PREFIX + email;
        Object code = redisTemplate.opsForValue().get(key);
        return code != null ? code.toString() : null;
    }

    /**
     * 删除邮箱验证码
     * 
     * @param email 邮箱
     */
    public void deleteEmailVerificationCode(String email) {
        String key = EMAIL_VERIFICATION_PREFIX + email;
        redisTemplate.delete(key);
    }

    /**
     * 存储密码重置验证码
     * 
     * @param email 邮箱
     * @param code 验证码
     * @param expireMinutes 过期时间（分钟）
     */
    public void storePasswordResetCode(String email, String code, long expireMinutes) {
        String key = PASSWORD_RESET_PREFIX + email;
        redisTemplate.opsForValue().set(key, code, expireMinutes, TimeUnit.MINUTES);
    }

    /**
     * 获取密码重置验证码
     * 
     * @param email 邮箱
     * @return 验证码
     */
    public String getPasswordResetCode(String email) {
        String key = PASSWORD_RESET_PREFIX + email;
        Object code = redisTemplate.opsForValue().get(key);
        return code != null ? code.toString() : null;
    }

    /**
     * 删除密码重置验证码
     * 
     * @param email 邮箱
     */
    public void deletePasswordResetCode(String email) {
        String key = PASSWORD_RESET_PREFIX + email;
        redisTemplate.delete(key);
    }

    /**
     * 增加登录失败次数
     * 
     * @param identifier 标识符（用户名或邮箱）
     * @param expireMinutes 过期时间（分钟）
     * @return 当前失败次数
     */
    public long incrementLoginAttempts(String identifier, long expireMinutes) {
        String key = LOGIN_ATTEMPTS_PREFIX + identifier;
        Long attempts = redisTemplate.opsForValue().increment(key);
        if (attempts == 1) {
            redisTemplate.expire(key, expireMinutes, TimeUnit.MINUTES);
        }
        return attempts;
    }

    /**
     * 获取登录失败次数
     * 
     * @param identifier 标识符（用户名或邮箱）
     * @return 失败次数
     */
    public long getLoginAttempts(String identifier) {
        String key = LOGIN_ATTEMPTS_PREFIX + identifier;
        Object attempts = redisTemplate.opsForValue().get(key);
        return attempts != null ? Long.parseLong(attempts.toString()) : 0;
    }

    /**
     * 清除登录失败次数
     * 
     * @param identifier 标识符（用户名或邮箱）
     */
    public void clearLoginAttempts(String identifier) {
        String key = LOGIN_ATTEMPTS_PREFIX + identifier;
        redisTemplate.delete(key);
    }

    /**
     * 设置缓存
     * 
     * @param key 键
     * @param value 值
     * @param timeout 超时时间
     * @param unit 时间单位
     */
    public void set(String key, Object value, long timeout, TimeUnit unit) {
        redisTemplate.opsForValue().set(key, value, timeout, unit);
    }

    /**
     * 获取缓存
     * 
     * @param key 键
     * @return 值
     */
    public Object get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    /**
     * 删除缓存
     * 
     * @param key 键
     */
    public void delete(String key) {
        redisTemplate.delete(key);
    }

    /**
     * 检查键是否存在
     * 
     * @param key 键
     * @return 是否存在
     */
    public boolean hasKey(String key) {
        return redisTemplate.hasKey(key);
    }
}