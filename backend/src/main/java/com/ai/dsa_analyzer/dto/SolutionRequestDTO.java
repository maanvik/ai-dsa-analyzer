package com.ai.dsa_analyzer.dto;

public class SolutionRequestDTO {
    private String problemStatement;
    private String username;

    public SolutionRequestDTO() {
    }

    public SolutionRequestDTO(String problemStatement, String username) {
        this.problemStatement = problemStatement;
        this.username = username;
    }

    public String getProblemStatement() {
        return problemStatement;
    }

    public void setProblemStatement(String problemStatement) {
        this.problemStatement = problemStatement;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
} 