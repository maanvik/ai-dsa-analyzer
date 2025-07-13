package com.ai.dsa_analyzer.dto;

public class SolutionResponseDTO {
    private String steps;

    public SolutionResponseDTO() {
    }

    public SolutionResponseDTO(String steps) {
        this.steps = steps;
    }

    public String getSteps() {
        return steps;
    }

    public void setSteps(String steps) {
        this.steps = steps;
    }
} 