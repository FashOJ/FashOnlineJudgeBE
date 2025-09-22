package com.fashoj.user.service;

import com.fashoj.user.dto.*;
import com.fashoj.user.entity.User;
import com.fashoj.user.repository.UserRepository;
import com.fashoj.user.util.JwtUtil;
import com.fashoj.user.util.PasswordUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * 认证服务
 * 实现所有认证相关的核心业务方法
 * 
 * @author Lingbou
 * @version 1.0
 * @since 2025-09-22
 */
@Service
@Transactional
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private PasswordUtil passwordUtil;

    @Autowired
    private EmailService emailService;

    @Autowired
    private RedisService redisService;

    // 最大登录失败次数
    private static final long MAX_LOGIN_ATTEMPTS = 5;
    // 账户锁定时间（分钟）
    private static final long ACCOUNT_LOCK_MINUTES = 30;

    /**
     * 用户注册
     * 
     * @param request 注册请求
     * @return 认证响应
     */
    public AuthResponse register(RegisterRequest request) {
        // 验证用户名和邮箱是否已存在
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("用户名已存在");
        }
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("邮箱已被注册");
        }

        // 验证密码强度
        if (!passwordUtil.isPasswordStrong(request.getPassword())) {
            throw new RuntimeException("密码强度不足，必须包含字母和数字，长度6-50位");
        }

        // 验证确认密码
        if (!request.getPassword().equals(request.getConfirmPassword())) {
            throw new RuntimeException("两次输入的密码不一致");
        }

        // 创建用户
        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordUtil.encodePassword(request.getPassword()));
        user.setNickname(request.getNickname());
        user.setRole(User.UserRole.USER);
        user.setStatus(User.UserStatus.ACTIVE);
        user.setEmailVerified(false);
        user.setLoginCount(0);
        user.setSolvedCount(0);
        user.setSubmissionCount(0);
        user.setAcceptedCount(0);

        user = userRepository.save(user);

        // 发送邮箱验证码
        emailService.sendEmailVerificationCode(user.getEmail());

        // 生成Token
        String accessToken = jwtUtil.generateAccessToken(user.getId(), user.getUsername(), user.getRole().name());
        String refreshToken = jwtUtil.generateRefreshToken(user.getId(), user.getUsername());

        // 构建响应
        UserInfo userInfo = UserInfo.fromUser(user);
        return new AuthResponse(userInfo, accessToken, refreshToken, jwtUtil.getAccessTokenExpiration());
    }

    /**
     * 用户登录
     * 
     * @param request 登录请求
     * @return 认证响应
     */
    public AuthResponse login(LoginRequest request) {
        String identifier = request.getLoginId(); // 使用登录标识符（用户名或邮箱）
        
        // 检查登录失败次数
        long attempts = redisService.getLoginAttempts(identifier);
        if (attempts >= MAX_LOGIN_ATTEMPTS) {
            throw new RuntimeException("登录失败次数过多，账户已被锁定" + ACCOUNT_LOCK_MINUTES + "分钟");
        }

        // 查找用户
        User user = userRepository.findByUsernameOrEmail(identifier, identifier)
                .orElseThrow(() -> {
                    redisService.incrementLoginAttempts(identifier, ACCOUNT_LOCK_MINUTES);
                    return new RuntimeException("用户名或密码错误");
                });

        // 验证密码
        if (!passwordUtil.matches(request.getPassword(), user.getPassword())) {
            redisService.incrementLoginAttempts(identifier, ACCOUNT_LOCK_MINUTES);
            throw new RuntimeException("用户名或密码错误");
        }

        // 检查用户状态
        if (user.getStatus() == User.UserStatus.BANNED) {
            throw new RuntimeException("账户已被封禁");
        }
        if (user.getStatus() == User.UserStatus.INACTIVE) {
            throw new RuntimeException("账户未激活");
        }

        // 清除登录失败次数
        redisService.clearLoginAttempts(identifier);

        // 更新最后登录时间
        userRepository.updateLastLoginTime(user.getId(), LocalDateTime.now());

        // 生成Token
        String accessToken = jwtUtil.generateAccessToken(user.getId(), user.getUsername(), user.getRole().name());
        String refreshToken = jwtUtil.generateRefreshToken(user.getId(), user.getUsername());

        // 构建响应
        UserInfo userInfo = UserInfo.fromUser(user);
        return new AuthResponse(userInfo, accessToken, refreshToken, jwtUtil.getAccessTokenExpiration());
    }

    /**
     * 用户登出
     * 
     * @param request 登出请求
     * @param accessToken 访问Token
     * @param refreshToken 刷新Token
     */
    public void logout(LogoutRequest request, String accessToken, String refreshToken) {
        try {
            // 将Token加入黑名单
            if (accessToken != null && jwtUtil.validateAccessToken(accessToken)) {
                long expireTime = jwtUtil.extractExpiration(accessToken).getTime() - System.currentTimeMillis();
                if (expireTime > 0) {
                    redisService.addTokenToBlacklist(accessToken, expireTime / 1000);
                }
            }

            if (refreshToken != null && jwtUtil.validateRefreshToken(refreshToken)) {
                long expireTime = jwtUtil.extractExpiration(refreshToken).getTime() - System.currentTimeMillis();
                if (expireTime > 0) {
                    redisService.addTokenToBlacklist(refreshToken, expireTime / 1000);
                }
            }

            // 如果是登出所有设备，可以在这里实现相关逻辑
            if (Boolean.TRUE.equals(request.getLogoutAll())) {
                // TODO: 实现登出所有设备的逻辑
                // 可以通过用户ID查找所有活跃的Token并加入黑名单
            }
        } catch (Exception e) {
            // 登出失败不应该影响用户体验，记录日志即可
            System.err.println("登出时处理Token失败: " + e.getMessage());
        }
    }

    /**
     * 刷新Token
     * 
     * @param request 刷新Token请求
     * @return Token响应
     */
    public TokenResponse refreshToken(RefreshTokenRequest request) {
        String refreshToken = request.getRefreshToken();

        // 验证刷新Token
        if (!jwtUtil.validateRefreshToken(refreshToken)) {
            throw new RuntimeException("刷新Token无效或已过期");
        }

        // 检查Token是否在黑名单中
        if (redisService.isTokenBlacklisted(refreshToken)) {
            throw new RuntimeException("刷新Token已失效");
        }

        // 从Token中提取用户信息
        Long userId = jwtUtil.extractUserId(refreshToken);
        String username = jwtUtil.extractUsername(refreshToken);

        // 查找用户
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("用户不存在"));

        // 检查用户状态
        if (user.getStatus() != User.UserStatus.ACTIVE) {
            throw new RuntimeException("用户账户异常");
        }

        // 将旧的刷新Token加入黑名单
        long expireTime = jwtUtil.extractExpiration(refreshToken).getTime() - System.currentTimeMillis();
        if (expireTime > 0) {
            redisService.addTokenToBlacklist(refreshToken, expireTime / 1000);
        }

        // 生成新的Token
        String newAccessToken = jwtUtil.generateAccessToken(user.getId(), user.getUsername(), user.getRole().name());
        String newRefreshToken = jwtUtil.generateRefreshToken(user.getId(), user.getUsername());

        return new TokenResponse(newAccessToken, newRefreshToken, jwtUtil.getAccessTokenExpiration());
    }

    /**
     * 忘记密码
     * 
     * @param request 忘记密码请求
     */
    public void forgotPassword(ForgotPasswordRequest request) {
        // 查找用户
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("邮箱未注册"));

        // 检查用户状态
        if (user.getStatus() == User.UserStatus.BANNED) {
            throw new RuntimeException("账户已被封禁");
        }

        // 发送密码重置验证码
        emailService.sendPasswordResetCode(user.getEmail());
    }

    /**
     * 重置密码
     * 
     * @param request 重置密码请求
     */
    public void resetPassword(ResetPasswordRequest request) {
        // 验证密码强度
        if (!passwordUtil.isPasswordStrong(request.getNewPassword())) {
            throw new RuntimeException("密码强度不足，必须包含字母和数字，长度6-50位");
        }

        // 验证确认密码
        if (!request.getNewPassword().equals(request.getConfirmPassword())) {
            throw new RuntimeException("两次输入的密码不一致");
        }

        // 验证重置验证码
        if (!emailService.verifyPasswordResetCode(request.getEmail(), request.getResetCode())) {
            throw new RuntimeException("重置验证码无效或已过期");
        }

        // 查找用户
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("邮箱未注册"));

        // 更新密码
        String encodedPassword = passwordUtil.encodePassword(request.getNewPassword());
        userRepository.updatePassword(user.getId(), encodedPassword);
    }

    /**
     * 邮箱验证
     * 
     * @param request 邮箱验证请求
     */
    public void verifyEmail(VerifyEmailRequest request) {
        // 验证邮箱验证码
        if (!emailService.verifyEmailCode(request.getEmail(), request.getVerificationCode())) {
            throw new RuntimeException("验证码无效或已过期");
        }

        // 查找用户
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("邮箱未注册"));

        // 更新邮箱验证状态
        userRepository.updateEmailVerificationStatus(user.getId(), true);
    }

    /**
     * 验证Token是否有效
     * 
     * @param token Token
     * @return 是否有效
     */
    public boolean validateToken(String token) {
        try {
            return jwtUtil.validateAccessToken(token) && !redisService.isTokenBlacklisted(token);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 从Token中获取用户信息
     * 
     * @param token Token
     * @return 用户信息
     */
    public UserInfo getUserFromToken(String token) {
        if (!validateToken(token)) {
            throw new RuntimeException("Token无效");
        }

        Long userId = jwtUtil.extractUserId(token);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("用户不存在"));

        return UserInfo.fromUser(user);
    }
}