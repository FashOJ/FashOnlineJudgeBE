package com.fashoj.problem.entity;

import com.fashoj.common.data.entity.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

/**
 * 题目实体类
 */
@Entity
@Table(name = "problems")
public class Problem extends BaseEntity {

    @NotBlank(message = "题目标题不能为空")
    @Column(name = "title", nullable = false)
    private String title;

    @NotBlank(message = "题目描述不能为空")
    @Column(name = "description", nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(name = "input_format", columnDefinition = "TEXT")
    private String inputFormat;

    @Column(name = "output_format", columnDefinition = "TEXT")
    private String outputFormat;

    @Column(name = "sample_input", columnDefinition = "TEXT")
    private String sampleInput;

    @Column(name = "sample_output", columnDefinition = "TEXT")
    private String sampleOutput;

    @Column(name = "hint", columnDefinition = "TEXT")
    private String hint;

    @Enumerated(EnumType.STRING)
    @Column(name = "difficulty", nullable = false)
    private Difficulty difficulty = Difficulty.EASY;

    @NotNull(message = "时间限制不能为空")
    @Positive(message = "时间限制必须为正数")
    @Column(name = "time_limit", nullable = false)
    private Integer timeLimit; // 毫秒

    @NotNull(message = "内存限制不能为空")
    @Positive(message = "内存限制必须为正数")
    @Column(name = "memory_limit", nullable = false)
    private Integer memoryLimit; // MB

    @Column(name = "test_data_url")
    private String testDataUrl;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private ProblemStatus status = ProblemStatus.DRAFT;

    @Column(name = "author_id", nullable = false)
    private Long authorId;

    @Column(name = "submission_count", nullable = false)
    private Integer submissionCount = 0;

    @Column(name = "accepted_count", nullable = false)
    private Integer acceptedCount = 0;

    @Column(name = "tags")
    private String tags; // JSON格式存储标签

    // 难度枚举
    public enum Difficulty {
        EASY, MEDIUM, HARD
    }

    // 题目状态枚举
    public enum ProblemStatus {
        DRAFT, PUBLISHED, HIDDEN
    }

    // Getters and Setters
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getInputFormat() {
        return inputFormat;
    }

    public void setInputFormat(String inputFormat) {
        this.inputFormat = inputFormat;
    }

    public String getOutputFormat() {
        return outputFormat;
    }

    public void setOutputFormat(String outputFormat) {
        this.outputFormat = outputFormat;
    }

    public String getSampleInput() {
        return sampleInput;
    }

    public void setSampleInput(String sampleInput) {
        this.sampleInput = sampleInput;
    }

    public String getSampleOutput() {
        return sampleOutput;
    }

    public void setSampleOutput(String sampleOutput) {
        this.sampleOutput = sampleOutput;
    }

    public String getHint() {
        return hint;
    }

    public void setHint(String hint) {
        this.hint = hint;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
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

    public ProblemStatus getStatus() {
        return status;
    }

    public void setStatus(ProblemStatus status) {
        this.status = status;
    }

    public Long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }

    public Integer getSubmissionCount() {
        return submissionCount;
    }

    public void setSubmissionCount(Integer submissionCount) {
        this.submissionCount = submissionCount;
    }

    public Integer getAcceptedCount() {
        return acceptedCount;
    }

    public void setAcceptedCount(Integer acceptedCount) {
        this.acceptedCount = acceptedCount;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }
}