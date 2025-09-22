package com.fashoj.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Random;

/**
 * 邮件服务
 * 实现验证码和密码重置邮件发送功能
 * 
 * @author Lingbou
 * @version 1.0
 * @since 2025-09-22
 */
@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private RedisService redisService;

    @Value("${spring.mail.username}")
    private String fromEmail;

    @Value("${app.name:FashOJ}")
    private String appName;

    // 验证码有效期（分钟）
    private static final long VERIFICATION_CODE_EXPIRE_MINUTES = 10;
    private static final long PASSWORD_RESET_CODE_EXPIRE_MINUTES = 15;

    /**
     * 发送邮箱验证码
     * 
     * @param email 邮箱地址
     * @return 验证码
     */
    public String sendEmailVerificationCode(String email) {
        String code = generateVerificationCode();
        
        // 存储验证码到Redis
        redisService.storeEmailVerificationCode(email, code, VERIFICATION_CODE_EXPIRE_MINUTES);
        
        // 发送邮件
        String subject = appName + " - 邮箱验证码";
        String content = buildEmailVerificationContent(code);
        sendSimpleEmail(email, subject, content);
        
        return code;
    }

    /**
     * 发送密码重置验证码
     * 
     * @param email 邮箱地址
     * @return 验证码
     */
    public String sendPasswordResetCode(String email) {
        String code = generateVerificationCode();
        
        // 存储验证码到Redis
        redisService.storePasswordResetCode(email, code, PASSWORD_RESET_CODE_EXPIRE_MINUTES);
        
        // 发送邮件
        String subject = appName + " - 密码重置验证码";
        String content = buildPasswordResetContent(code);
        sendSimpleEmail(email, subject, content);
        
        return code;
    }

    /**
     * 验证邮箱验证码
     * 
     * @param email 邮箱地址
     * @param code 验证码
     * @return 是否验证成功
     */
    public boolean verifyEmailCode(String email, String code) {
        String storedCode = redisService.getEmailVerificationCode(email);
        if (storedCode != null && storedCode.equals(code)) {
            redisService.deleteEmailVerificationCode(email);
            return true;
        }
        return false;
    }

    /**
     * 验证密码重置验证码
     * 
     * @param email 邮箱地址
     * @param code 验证码
     * @return 是否验证成功
     */
    public boolean verifyPasswordResetCode(String email, String code) {
        String storedCode = redisService.getPasswordResetCode(email);
        if (storedCode != null && storedCode.equals(code)) {
            redisService.deletePasswordResetCode(email);
            return true;
        }
        return false;
    }

    /**
     * 发送简单邮件
     * 
     * @param to 收件人
     * @param subject 主题
     * @param content 内容
     */
    private void sendSimpleEmail(String to, String subject, String content) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(fromEmail);
            message.setTo(to);
            message.setSubject(subject);
            message.setText(content);
            
            mailSender.send(message);
        } catch (Exception e) {
            throw new RuntimeException("邮件发送失败: " + e.getMessage(), e);
        }
    }

    /**
     * 生成6位数字验证码
     * 
     * @return 验证码
     */
    private String generateVerificationCode() {
        Random random = new Random();
        int code = 100000 + random.nextInt(900000);
        return String.valueOf(code);
    }

    /**
     * 构建邮箱验证邮件内容
     * 
     * @param code 验证码
     * @return 邮件内容
     */
    private String buildEmailVerificationContent(String code) {
        return String.format(
            "亲爱的用户，\n\n" +
            "您正在进行邮箱验证，验证码为：%s\n\n" +
            "验证码有效期为 %d 分钟，请及时使用。\n\n" +
            "如果这不是您的操作，请忽略此邮件。\n\n" +
            "此邮件由系统自动发送，请勿回复。\n\n" +
            "%s 团队",
            code, VERIFICATION_CODE_EXPIRE_MINUTES, appName
        );
    }

    /**
     * 构建密码重置邮件内容
     * 
     * @param code 验证码
     * @return 邮件内容
     */
    private String buildPasswordResetContent(String code) {
        return String.format(
            "亲爱的用户，\n\n" +
            "您正在进行密码重置，验证码为：%s\n\n" +
            "验证码有效期为 %d 分钟，请及时使用。\n\n" +
            "如果这不是您的操作，请立即检查您的账户安全。\n\n" +
            "此邮件由系统自动发送，请勿回复。\n\n" +
            "%s 团队",
            code, PASSWORD_RESET_CODE_EXPIRE_MINUTES, appName
        );
    }
}