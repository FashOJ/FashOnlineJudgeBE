package com.fashoj.user.repository;

import com.fashoj.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * 用户数据访问接口
 * 
 * @author Lingbou
 * @version 1.0
 * @since 2025-09-22
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * 根据用户名查找用户
     * 
     * @param username 用户名
     * @return 用户信息
     */
    Optional<User> findByUsername(String username);

    /**
     * 根据邮箱查找用户
     * 
     * @param email 邮箱
     * @return 用户信息
     */
    Optional<User> findByEmail(String email);

    /**
     * 根据用户名或邮箱查找用户
     * 
     * @param username 用户名
     * @param email 邮箱
     * @return 用户信息
     */
    Optional<User> findByUsernameOrEmail(String username, String email);

    /**
     * 检查用户名是否存在
     * 
     * @param username 用户名
     * @return 是否存在
     */
    boolean existsByUsername(String username);

    /**
     * 检查邮箱是否存在
     * 
     * @param email 邮箱
     * @return 是否存在
     */
    boolean existsByEmail(String email);

    /**
     * 检查用户名或邮箱是否存在（排除指定用户ID）
     * 
     * @param username 用户名
     * @param email 邮箱
     * @param userId 排除的用户ID
     * @return 是否存在
     */
    boolean existsByUsernameOrEmailAndIdNot(String username, String email, Long userId);

    /**
     * 更新用户最后登录时间
     * 
     * @param userId 用户ID
     * @param lastLoginTime 最后登录时间
     */
    @Modifying
    @Query("UPDATE User u SET u.lastLoginTime = :lastLoginTime, u.loginCount = u.loginCount + 1 WHERE u.id = :userId")
    void updateLastLoginTime(@Param("userId") Long userId, @Param("lastLoginTime") LocalDateTime lastLoginTime);

    /**
     * 更新用户邮箱验证状态
     * 
     * @param userId 用户ID
     * @param emailVerified 邮箱验证状态
     */
    @Modifying
    @Query("UPDATE User u SET u.emailVerified = :emailVerified WHERE u.id = :userId")
    void updateEmailVerificationStatus(@Param("userId") Long userId, @Param("emailVerified") Boolean emailVerified);

    /**
     * 更新用户密码
     * 
     * @param userId 用户ID
     * @param password 新密码
     */
    @Modifying
    @Query("UPDATE User u SET u.password = :password WHERE u.id = :userId")
    void updatePassword(@Param("userId") Long userId, @Param("password") String password);

    /**
     * 根据用户状态查找用户
     * 
     * @param status 用户状态
     * @return 用户列表
     */
    @Query("SELECT u FROM User u WHERE u.status = :status")
    java.util.List<User> findByStatus(@Param("status") User.UserStatus status);

    /**
     * 根据用户角色查找用户
     * 
     * @param role 用户角色
     * @return 用户列表
     */
    @Query("SELECT u FROM User u WHERE u.role = :role")
    java.util.List<User> findByRole(@Param("role") User.UserRole role);
}