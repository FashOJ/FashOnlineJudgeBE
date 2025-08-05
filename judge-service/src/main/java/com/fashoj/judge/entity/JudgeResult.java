package com.fashoj.judge.entity;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 判题结果实体类，存储代码执行后的判题结果信息
 * 
 * @author Lingbou
 * @version 1.0
 * @since 2025-08-06
 */
public class JudgeResult {

    private Long submissionId;
    private String status;
    private Integer score;
    private Integer timeUsed;
    private Integer memoryUsed;
    private String compileInfo;
    private String runtimeInfo;
    private String judgeInfo;
    private Integer testCasesPassed;
    private Integer totalTestCases;
    private List<TestCaseResult> testCaseResults;
    private LocalDateTime judgedAt;

    public JudgeResult() {
        this.judgedAt = LocalDateTime.now();
    }

    public JudgeResult(Long submissionId) {
        this();
        this.submissionId = submissionId;
    }

    /**
     * 测试用例结果
     */
    public static class TestCaseResult {
        private Integer caseNumber;
        private String status;
        private Integer timeUsed;
        private Integer memoryUsed;
        private String input;
        private String expectedOutput;
        private String actualOutput;
        private String errorInfo;

        public TestCaseResult() {}

        public TestCaseResult(Integer caseNumber, String status) {
            this.caseNumber = caseNumber;
            this.status = status;
        }

        // Getters and Setters
        public Integer getCaseNumber() {
            return caseNumber;
        }

        public void setCaseNumber(Integer caseNumber) {
            this.caseNumber = caseNumber;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
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

        public String getInput() {
            return input;
        }

        public void setInput(String input) {
            this.input = input;
        }

        public String getExpectedOutput() {
            return expectedOutput;
        }

        public void setExpectedOutput(String expectedOutput) {
            this.expectedOutput = expectedOutput;
        }

        public String getActualOutput() {
            return actualOutput;
        }

        public void setActualOutput(String actualOutput) {
            this.actualOutput = actualOutput;
        }

        public String getErrorInfo() {
            return errorInfo;
        }

        public void setErrorInfo(String errorInfo) {
            this.errorInfo = errorInfo;
        }
    }

    // Getters and Setters
    public Long getSubmissionId() {
        return submissionId;
    }

    public void setSubmissionId(Long submissionId) {
        this.submissionId = submissionId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
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

    public List<TestCaseResult> getTestCaseResults() {
        return testCaseResults;
    }

    public void setTestCaseResults(List<TestCaseResult> testCaseResults) {
        this.testCaseResults = testCaseResults;
    }

    public LocalDateTime getJudgedAt() {
        return judgedAt;
    }

    public void setJudgedAt(LocalDateTime judgedAt) {
        this.judgedAt = judgedAt;
    }
}