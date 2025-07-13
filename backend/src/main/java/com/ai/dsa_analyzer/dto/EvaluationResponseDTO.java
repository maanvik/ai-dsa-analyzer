package com.ai.dsa_analyzer.dto;

public class EvaluationResponseDTO {
    private boolean correct;
    private String feedback;

    public EvaluationResponseDTO() {
    }

    public boolean isCorrect() {
        return correct;
    }

    public void setCorrect(boolean correct) {
        this.correct = correct;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }
}
