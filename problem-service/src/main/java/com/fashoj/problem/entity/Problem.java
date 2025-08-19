package com.fashoj.problem.entity;

import com.fashoj.common.data.entity.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.DecimalMax;
import java.time.LocalDateTime;
import java.math.BigDecimal;

/**
 * 题目实体类，存储编程题目的详细信息
 * 
 * @author Lingbou
 * @version 1.0
 * @since 2025-08-06
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

    // 题目分类相关字段
    @Column(name = "category", length = 50)
    private String category;

    @Column(name = "source", length = 100)
    private String source;

    @Column(name = "contest_id")
    private Long contestId;

    @Column(name = "problem_order", length = 5)
    private String problemOrder;

    // 判题配置相关字段
    @Enumerated(EnumType.STRING)
    @Column(name = "judge_mode", nullable = false)
    private JudgeMode judgeMode = JudgeMode.ACM;

    @Column(name = "special_judge", nullable = false)
    private Boolean specialJudge = false;

    @Column(name = "interactive_mode", nullable = false)
    private Boolean interactiveMode = false;

    @Column(name = "allowed_languages", columnDefinition = "TEXT")
    private String allowedLanguages; // JSON格式存储允许的编程语言

    @Column(name = "code_template", columnDefinition = "TEXT")
    private String codeTemplate;

    // 统计和管理相关字段
    @Column(name = "view_count", nullable = false)
    private Integer viewCount = 0;

    @Column(name = "like_count", nullable = false)
    private Integer likeCount = 0;

    @DecimalMin(value = "0.0", message = "难度评分不能小于0")
    @DecimalMax(value = "10.0", message = "难度评分不能大于10")
    @Column(name = "difficulty_rating", precision = 3, scale = 1)
    private BigDecimal difficultyRating;

    @Column(name = "last_modified_by")
    private Long lastModifiedBy;

    @Column(name = "published_at")
    private LocalDateTime publishedAt;

    @Column(name = "is_public", nullable = false)
    private Boolean isPublic = true;

    // 判题模式枚举
    public enum JudgeMode {
        ACM, OI
    }

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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Long getContestId() {
        return contestId;
    }

    public void setContestId(Long contestId) {
        this.contestId = contestId;
    }

    public String getProblemOrder() {
        return problemOrder;
    }

    public void setProblemOrder(String problemOrder) {
        this.problemOrder = problemOrder;
    }

    public JudgeMode getJudgeMode() {
        return judgeMode;
    }

    public void setJudgeMode(JudgeMode judgeMode) {
        this.judgeMode = judgeMode;
    }

    public Boolean getSpecialJudge() {
        return specialJudge;
    }

    public void setSpecialJudge(Boolean specialJudge) {
        this.specialJudge = specialJudge;
    }

    public Boolean getInteractiveMode() {
        return interactiveMode;
    }

    public void setInteractiveMode(Boolean interactiveMode) {
        this.interactiveMode = interactiveMode;
    }

    public String getAllowedLanguages() {
        return allowedLanguages;
    }

    public void setAllowedLanguages(String allowedLanguages) {
        this.allowedLanguages = allowedLanguages;
    }

    public String getCodeTemplate() {
        return codeTemplate;
    }

    public void setCodeTemplate(String codeTemplate) {
        this.codeTemplate = codeTemplate;
    }

    public Integer getViewCount() {
        return viewCount;
    }

    public void setViewCount(Integer viewCount) {
        this.viewCount = viewCount;
    }

    public Integer getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(Integer likeCount) {
        this.likeCount = likeCount;
    }

    public BigDecimal getDifficultyRating() {
        return difficultyRating;
    }

    public void setDifficultyRating(BigDecimal difficultyRating) {
        this.difficultyRating = difficultyRating;
    }

    public Long getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(Long lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public LocalDateTime getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(LocalDateTime publishedAt) {
        this.publishedAt = publishedAt;
    }

    public Boolean getIsPublic() {
        return isPublic;
    }

    public void setIsPublic(Boolean isPublic) {
        this.isPublic = isPublic;
    }
}