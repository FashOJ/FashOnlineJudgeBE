package com.fashoj.user.dto;

import com.fashoj.user.entity.User;
import java.time.LocalDateTime;

/**
 * 用户信息DTO，用于返回用户基本信息
 * 
 * @author Lingbou
 * @version 1.0
 * @since 2025-09-22
 */
public class UserInfo {

    private Long id;
    private String username;
    private String email;
    private String nickname;
    private String avatarUrl;
    private User.UserRole role;
    private Boolean emailVerified;
    private LocalDateTime lastLoginTime;
    private String school;
    private Integer rating;
    private Integer solvedCount;
    private Integer submissionCount;

    // 构造函数
    public UserInfo() {}

    public UserInfo(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.nickname = user.getNickname();
        this.avatarUrl = user.getAvatarUrl();
        this.role = user.getRole();
        this.emailVerified = user.getEmailVerified();
        this.lastLoginTime = user.getLastLoginTime();
        this.school = user.getSchool();
        this.rating = user.getRating();
        this.solvedCount = user.getSolvedCount();
        this.submissionCount = user.getSubmissionCount();
    }

    // 静态工厂方法
    public static UserInfo fromUser(User user) {
        return new UserInfo(user);
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public User.UserRole getRole() {
        return role;
    }

    public void setRole(User.UserRole role) {
        this.role = role;
    }

    public Boolean getEmailVerified() {
        return emailVerified;
    }

    public void setEmailVerified(Boolean emailVerified) {
        this.emailVerified = emailVerified;
    }

    public LocalDateTime getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(LocalDateTime lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public Integer getSolvedCount() {
        return solvedCount;
    }

    public void setSolvedCount(Integer solvedCount) {
        this.solvedCount = solvedCount;
    }

    public Integer getSubmissionCount() {
        return submissionCount;
    }

    public void setSubmissionCount(Integer submissionCount) {
        this.submissionCount = submissionCount;
    }
}