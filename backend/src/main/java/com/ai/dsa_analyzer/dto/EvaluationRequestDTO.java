package com.ai.dsa_analyzer.dto;

public class EvaluationRequestDTO {
    private String problemStatement;
    private String userApproach;
    private String username;

    public EvaluationRequestDTO() {
    }

    public String getProblemStatement() {
        return problemStatement;
    }

    public void setProblemStatement(String problemStatement) {
        this.problemStatement = problemStatement;
    }

    public String getUserApproach() {
        return userApproach;
    }

    public void setUserApproach(String userApproach) {
        this.userApproach = userApproach;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
