package com.fashoj.user.exception;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 全局异常处理器
 * 统一处理认证相关异常和数据验证异常
 * 
 * @author Lingbou
 * @version 1.0
 * @since 2025-09-22
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
    
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    
    /**
     * 处理认证异常
     */
    @ExceptionHandler(AuthException.class)
    public ResponseEntity<Map<String, Object>> handleAuthException(AuthException ex, WebRequest request) {
        logger.warn("认证异常: {}", ex.getMessage());
        
        Map<String, Object> response = new HashMap<>();
        response.put("code", ex.getErrorCode());
        response.put("message", ex.getMessage());
        response.put("data", null);
        response.put("timestamp", System.currentTimeMillis());
        response.put("path", request.getDescription(false).replace("uri=", ""));
        
        return ResponseEntity.status(ex.getErrorCode()).body(response);
    }
    
    /**
     * 处理用户未找到异常
     */
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleUserNotFoundException(UserNotFoundException ex, WebRequest request) {
        logger.warn("用户未找到: {}", ex.getMessage());
        
        Map<String, Object> response = new HashMap<>();
        response.put("code", 404);
        response.put("message", ex.getMessage());
        response.put("data", null);
        response.put("timestamp", System.currentTimeMillis());
        response.put("path", request.getDescription(false).replace("uri=", ""));
        
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }
    
    /**
     * 处理用户已存在异常
     */
    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<Map<String, Object>> handleUserAlreadyExistsException(UserAlreadyExistsException ex, WebRequest request) {
        logger.warn("用户已存在: {}", ex.getMessage());
        
        Map<String, Object> response = new HashMap<>();
        response.put("code", 409);
        response.put("message", ex.getMessage());
        response.put("data", null);
        response.put("timestamp", System.currentTimeMillis());
        response.put("path", request.getDescription(false).replace("uri=", ""));
        
        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }
    
    /**
     * 处理无效凭据异常
     */
    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<Map<String, Object>> handleInvalidCredentialsException(InvalidCredentialsException ex, WebRequest request) {
        logger.warn("无效凭据: {}", ex.getMessage());
        
        Map<String, Object> response = new HashMap<>();
        response.put("code", 401);
        response.put("message", ex.getMessage());
        response.put("data", null);
        response.put("timestamp", System.currentTimeMillis());
        response.put("path", request.getDescription(false).replace("uri=", ""));
        
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }
    
    /**
     * 处理Token无效异常
     */
    @ExceptionHandler(InvalidTokenException.class)
    public ResponseEntity<Map<String, Object>> handleInvalidTokenException(InvalidTokenException ex, WebRequest request) {
        logger.warn("Token无效: {}", ex.getMessage());
        
        Map<String, Object> response = new HashMap<>();
        response.put("code", 401);
        response.put("message", ex.getMessage());
        response.put("data", null);
        response.put("timestamp", System.currentTimeMillis());
        response.put("path", request.getDescription(false).replace("uri=", ""));
        
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }
    
    /**
     * 处理账户锁定异常
     */
    @ExceptionHandler(AccountLockedException.class)
    public ResponseEntity<Map<String, Object>> handleAccountLockedException(AccountLockedException ex, WebRequest request) {
        logger.warn("账户锁定: {}", ex.getMessage());
        
        Map<String, Object> response = new HashMap<>();
        response.put("code", 423);
        response.put("message", ex.getMessage());
        response.put("data", Map.of("lockTimeRemaining", ex.getLockTimeRemaining()));
        response.put("timestamp", System.currentTimeMillis());
        response.put("path", request.getDescription(false).replace("uri=", ""));
        
        return ResponseEntity.status(HttpStatus.LOCKED).body(response);
    }
    
    /**
     * 处理验证码无效异常
     */
    @ExceptionHandler(InvalidVerificationCodeException.class)
    public ResponseEntity<Map<String, Object>> handleInvalidVerificationCodeException(InvalidVerificationCodeException ex, WebRequest request) {
        logger.warn("验证码无效: {}", ex.getMessage());
        
        Map<String, Object> response = new HashMap<>();
        response.put("code", 400);
        response.put("message", ex.getMessage());
        response.put("data", null);
        response.put("timestamp", System.currentTimeMillis());
        response.put("path", request.getDescription(false).replace("uri=", ""));
        
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
    
    /**
     * 处理数据验证异常 - @Valid注解触发
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex, WebRequest request) {
        logger.warn("数据验证失败: {}", ex.getMessage());
        
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        
        Map<String, Object> response = new HashMap<>();
        response.put("code", 400);
        response.put("message", "数据验证失败");
        response.put("data", Map.of("errors", errors));
        response.put("timestamp", System.currentTimeMillis());
        response.put("path", request.getDescription(false).replace("uri=", ""));
        
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
    
    /**
     * 处理绑定异常
     */
    @ExceptionHandler(BindException.class)
    public ResponseEntity<Map<String, Object>> handleBindException(BindException ex, WebRequest request) {
        logger.warn("数据绑定失败: {}", ex.getMessage());
        
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        
        Map<String, Object> response = new HashMap<>();
        response.put("code", 400);
        response.put("message", "数据绑定失败");
        response.put("data", Map.of("errors", errors));
        response.put("timestamp", System.currentTimeMillis());
        response.put("path", request.getDescription(false).replace("uri=", ""));
        
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
    
    /**
     * 处理约束违反异常
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Map<String, Object>> handleConstraintViolationException(ConstraintViolationException ex, WebRequest request) {
        logger.warn("约束违反: {}", ex.getMessage());
        
        String errors = ex.getConstraintViolations()
                .stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.joining(", "));
        
        Map<String, Object> response = new HashMap<>();
        response.put("code", 400);
        response.put("message", "数据验证失败: " + errors);
        response.put("data", null);
        response.put("timestamp", System.currentTimeMillis());
        response.put("path", request.getDescription(false).replace("uri=", ""));
        
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
    
    /**
     * 处理运行时异常
     */
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, Object>> handleRuntimeException(RuntimeException ex, WebRequest request) {
        logger.error("运行时异常: {}", ex.getMessage(), ex);
        
        Map<String, Object> response = new HashMap<>();
        response.put("code", 500);
        response.put("message", "服务器内部错误");
        response.put("data", null);
        response.put("timestamp", System.currentTimeMillis());
        response.put("path", request.getDescription(false).replace("uri=", ""));
        
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
    
    /**
     * 处理通用异常
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGenericException(Exception ex, WebRequest request) {
        logger.error("未处理异常: {}", ex.getMessage(), ex);
        
        Map<String, Object> response = new HashMap<>();
        response.put("code", 500);
        response.put("message", "服务器内部错误");
        response.put("data", null);
        response.put("timestamp", System.currentTimeMillis());
        response.put("path", request.getDescription(false).replace("uri=", ""));
        
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}