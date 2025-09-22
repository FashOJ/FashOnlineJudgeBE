package com.fashoj.user.controller;

import com.fashoj.user.dto.*;
import com.fashoj.user.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 认证控制器
 * 实现7个认证接口的HTTP端点
 * 
 * @author Lingbou
 * @version 1.0
 * @since 2025-09-22
 */
@RestController
@RequestMapping("/api/auth")
@Tag(name = "认证管理", description = "用户认证相关接口")
public class AuthController {

    @Autowired
    private AuthService authService;

    /**
     * 用户注册
     */
    @PostMapping("/register")
    @Operation(summary = "用户注册", description = "新用户注册接口")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "注册成功"),
            @ApiResponse(responseCode = "400", description = "请求参数错误"),
            @ApiResponse(responseCode = "409", description = "用户名或邮箱已存在")
    })
    public ResponseEntity<Map<String, Object>> register(
            @Valid @RequestBody RegisterRequest request) {
        try {
            AuthResponse response = authService.register(request);
            
            Map<String, Object> result = new HashMap<>();
            result.put("code", 200);
            result.put("message", "注册成功");
            result.put("data", response);
            
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            Map<String, Object> result = new HashMap<>();
            result.put("code", 400);
            result.put("message", e.getMessage());
            result.put("data", null);
            
            return ResponseEntity.badRequest().body(result);
        }
    }

    /**
     * 用户登录
     */
    @PostMapping("/login")
    @Operation(summary = "用户登录", description = "用户登录接口")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "登录成功"),
            @ApiResponse(responseCode = "401", description = "用户名或密码错误"),
            @ApiResponse(responseCode = "423", description = "账户被锁定")
    })
    public ResponseEntity<Map<String, Object>> login(
            @Valid @RequestBody LoginRequest request) {
        try {
            AuthResponse response = authService.login(request);
            
            Map<String, Object> result = new HashMap<>();
            result.put("code", 200);
            result.put("message", "登录成功");
            result.put("data", response);
            
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            Map<String, Object> result = new HashMap<>();
            result.put("code", 401);
            result.put("message", e.getMessage());
            result.put("data", null);
            
            return ResponseEntity.status(401).body(result);
        }
    }

    /**
     * 用户登出
     */
    @PostMapping("/logout")
    @Operation(summary = "用户登出", description = "用户登出接口")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "登出成功"),
            @ApiResponse(responseCode = "401", description = "未授权")
    })
    public ResponseEntity<Map<String, Object>> logout(
            @RequestBody(required = false) LogoutRequest request,
            HttpServletRequest httpRequest) {
        try {
            if (request == null) {
                request = new LogoutRequest();
            }
            
            // 从请求头中获取Token
            String accessToken = extractTokenFromRequest(httpRequest);
            String refreshToken = httpRequest.getHeader("Refresh-Token");
            
            authService.logout(request, accessToken, refreshToken);
            
            Map<String, Object> result = new HashMap<>();
            result.put("code", 200);
            result.put("message", "登出成功");
            result.put("data", null);
            
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            Map<String, Object> result = new HashMap<>();
            result.put("code", 500);
            result.put("message", "登出失败: " + e.getMessage());
            result.put("data", null);
            
            return ResponseEntity.status(500).body(result);
        }
    }

    /**
     * 刷新Token
     */
    @PostMapping("/refresh")
    @Operation(summary = "刷新Token", description = "刷新访问Token接口")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "刷新成功"),
            @ApiResponse(responseCode = "401", description = "刷新Token无效")
    })
    public ResponseEntity<Map<String, Object>> refreshToken(
            @Valid @RequestBody RefreshTokenRequest request) {
        try {
            TokenResponse response = authService.refreshToken(request);
            
            Map<String, Object> result = new HashMap<>();
            result.put("code", 200);
            result.put("message", "Token刷新成功");
            result.put("data", response);
            
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            Map<String, Object> result = new HashMap<>();
            result.put("code", 401);
            result.put("message", e.getMessage());
            result.put("data", null);
            
            return ResponseEntity.status(401).body(result);
        }
    }

    /**
     * 忘记密码
     */
    @PostMapping("/forgot-password")
    @Operation(summary = "忘记密码", description = "发送密码重置验证码接口")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "验证码发送成功"),
            @ApiResponse(responseCode = "404", description = "邮箱未注册")
    })
    public ResponseEntity<Map<String, Object>> forgotPassword(
            @Valid @RequestBody ForgotPasswordRequest request) {
        try {
            authService.forgotPassword(request);
            
            Map<String, Object> result = new HashMap<>();
            result.put("code", 200);
            result.put("message", "密码重置验证码已发送到您的邮箱");
            result.put("data", null);
            
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            Map<String, Object> result = new HashMap<>();
            result.put("code", 404);
            result.put("message", e.getMessage());
            result.put("data", null);
            
            return ResponseEntity.status(404).body(result);
        }
    }

    /**
     * 重置密码
     */
    @PostMapping("/reset-password")
    @Operation(summary = "重置密码", description = "使用验证码重置密码接口")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "密码重置成功"),
            @ApiResponse(responseCode = "400", description = "验证码无效或密码格式错误")
    })
    public ResponseEntity<Map<String, Object>> resetPassword(
            @Valid @RequestBody ResetPasswordRequest request) {
        try {
            authService.resetPassword(request);
            
            Map<String, Object> result = new HashMap<>();
            result.put("code", 200);
            result.put("message", "密码重置成功");
            result.put("data", null);
            
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            Map<String, Object> result = new HashMap<>();
            result.put("code", 400);
            result.put("message", e.getMessage());
            result.put("data", null);
            
            return ResponseEntity.badRequest().body(result);
        }
    }

    /**
     * 邮箱验证
     */
    @PostMapping("/verify-email")
    @Operation(summary = "邮箱验证", description = "验证邮箱接口")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "邮箱验证成功"),
            @ApiResponse(responseCode = "400", description = "验证码无效")
    })
    public ResponseEntity<Map<String, Object>> verifyEmail(
            @Valid @RequestBody VerifyEmailRequest request) {
        try {
            authService.verifyEmail(request);
            
            Map<String, Object> result = new HashMap<>();
            result.put("code", 200);
            result.put("message", "邮箱验证成功");
            result.put("data", null);
            
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            Map<String, Object> result = new HashMap<>();
            result.put("code", 400);
            result.put("message", e.getMessage());
            result.put("data", null);
            
            return ResponseEntity.badRequest().body(result);
        }
    }

    /**
     * 获取当前用户信息
     */
    @GetMapping("/me")
    @Operation(summary = "获取当前用户信息", description = "根据Token获取当前用户信息")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "获取成功"),
            @ApiResponse(responseCode = "401", description = "Token无效")
    })
    public ResponseEntity<Map<String, Object>> getCurrentUser(
            HttpServletRequest request) {
        try {
            String token = extractTokenFromRequest(request);
            if (token == null) {
                throw new RuntimeException("Token不能为空");
            }
            
            UserInfo userInfo = authService.getUserFromToken(token);
            
            Map<String, Object> result = new HashMap<>();
            result.put("code", 200);
            result.put("message", "获取用户信息成功");
            result.put("data", userInfo);
            
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            Map<String, Object> result = new HashMap<>();
            result.put("code", 401);
            result.put("message", e.getMessage());
            result.put("data", null);
            
            return ResponseEntity.status(401).body(result);
        }
    }

    /**
     * 从请求中提取Token
     * 
     * @param request HTTP请求
     * @return Token
     */
    private String extractTokenFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}