package com.fashoj.submission.entity;

import com.fashoj.common.data.entity.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * 提交记录实体类
 */
@Entity
@Table(name = "submissions")
public class Submission extends BaseEntity {

    @NotNull(message = "用户ID不能为空")
    @Column(name = "user_id", nullable = false)
    private Long userId;

    @NotNull(message = "题目ID不能为空")
    @Column(name = "problem_id", nullable = false)
    private Long problemId;

    @NotBlank(message = "代码不能为空")
    @Column(name = "code", nullable = false, columnDefinition = "TEXT")
    private String code;

    @Enumerated(EnumType.STRING)
    @Column(name = "language", nullable = false)
    private Language language;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private SubmissionStatus status = SubmissionStatus.PENDING;

    @Column(name = "score")
    private Integer score;

    @Column(name = "time_used")
    private Integer timeUsed; // 毫秒

    @Column(name = "memory_used")
    private Integer memoryUsed; // KB

    @Column(name = "compile_info", columnDefinition = "TEXT")
    private String compileInfo;

    @Column(name = "runtime_info", columnDefinition = "TEXT")
    private String runtimeInfo;

    @Column(name = "judge_info", columnDefinition = "TEXT")
    private String judgeInfo;

    @Column(name = "test_cases_passed")
    private Integer testCasesPassed;

    @Column(name = "total_test_cases")
    private Integer totalTestCases;

    // 编程语言枚举
    public enum Language {
        C, CPP, JAVA, PYTHON, JAVASCRIPT, GO, RUST
    }

    // 提交状态枚举
    public enum SubmissionStatus {
        PENDING,           // 等待判题
        JUDGING,          // 判题中
        ACCEPTED,         // 通过
        WRONG_ANSWER,     // 答案错误
        TIME_LIMIT_EXCEEDED,    // 超时
        MEMORY_LIMIT_EXCEEDED,  // 内存超限
        RUNTIME_ERROR,    // 运行时错误
        COMPILE_ERROR,    // 编译错误
        SYSTEM_ERROR      // 系统错误
    }

    // Getters and Setters
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

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public SubmissionStatus getStatus() {
        return status;
    }

    public void setStatus(SubmissionStatus status) {
        this.status = status;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Integer getTimeUsed() {
        return timeUsed;
    }

    public void setTimeUsed(Integer timeUsed) {
        this.timeUsed = timeUsed;
    }

    public Integer getMemoryUsed() {
        return memoryUsed;
    }

    public void setMemoryUsed(Integer memoryUsed) {
        this.memoryUsed = memoryUsed;
    }

    public String getCompileInfo() {
        return compileInfo;
    }

    public void setCompileInfo(String compileInfo) {
        this.compileInfo = compileInfo;
    }

    public String getRuntimeInfo() {
        return runtimeInfo;
    }

    public void setRuntimeInfo(String runtimeInfo) {
        this.runtimeInfo = runtimeInfo;
    }

    public String getJudgeInfo() {
        return judgeInfo;
    }

    public void setJudgeInfo(String judgeInfo) {
        this.judgeInfo = judgeInfo;
    }

    public Integer getTestCasesPassed() {
        return testCasesPassed;
    }

    public void setTestCasesPassed(Integer testCasesPassed) {
        this.testCasesPassed = testCasesPassed;
    }

    public Integer getTotalTestCases() {
        return totalTestCases;
    }

    public void setTotalTestCases(Integer totalTestCases) {
        this.totalTestCases = totalTestCases;
    }
}