package com.fashoj.judge.entity;

import java.time.LocalDateTime;

/**
 * 判题任务实体类
 */
public class JudgeTask {

    private Long submissionId;
    private Long userId;
    private Long problemId;
    private String code;
    private String language;
    private Integer timeLimit;
    private Integer memoryLimit;
    private String testDataUrl;
    private LocalDateTime createdAt;
    private Integer priority;

    public JudgeTask() {
        this.createdAt = LocalDateTime.now();
        this.priority = 0;
    }

    public JudgeTask(Long submissionId, Long userId, Long problemId, String code, String language) {
        this();
        this.submissionId = submissionId;
        this.userId = userId;
        this.problemId = problemId;
        this.code = code;
        this.language = language;
    }

    // Getters and Setters
    public Long getSubmissionId() {
        return submissionId;
    }

    public void setSubmissionId(Long submissionId) {
        this.submissionId = submissionId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getProblemId() {
        return problemId;
    }

    public void setProblemId(Long problemId) {
        this.problemId = problemId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public Integer getTimeLimit() {
        return timeLimit;
    }

    public void setTimeLimit(Integer timeLimit) {
        this.timeLimit = timeLimit;
    }

    public Integer getMemoryLimit() {
        return memoryLimit;
    }

    public void setMemoryLimit(Integer memoryLimit) {
        this.memoryLimit = memoryLimit;
    }

    public String getTestDataUrl() {
        return testDataUrl;
    }

    public void setTestDataUrl(String testDataUrl) {
        this.testDataUrl = testDataUrl;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }
}