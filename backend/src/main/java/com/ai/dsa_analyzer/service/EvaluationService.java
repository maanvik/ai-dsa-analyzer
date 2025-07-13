package com.ai.dsa_analyzer.service;

import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.ai.dsa_analyzer.dto.EvaluationRequestDTO;
import com.ai.dsa_analyzer.dto.EvaluationResponseDTO;
import com.ai.dsa_analyzer.dto.SolutionRequestDTO;
import com.ai.dsa_analyzer.dto.SolutionResponseDTO;
import com.ai.dsa_analyzer.ai.OpenAIClient;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.regex.Pattern;

@Service
public class EvaluationService {

    private static final Logger logger = LoggerFactory.getLogger(EvaluationService.class);
    private final OpenAIClient openAIClient;
    
    // Pattern to detect code-like syntax
    private static final Pattern CODE_PATTERN = Pattern.compile(
        "\\b(public|private|protected|class|interface|void|int|String|boolean|if|else|for|while|return|new|import|package)\\b|" +
        "[{}();=<>!&|+\\-*/%]|" +
        "\\b\\w+\\s*\\([^)]*\\)|" +
        "\\b\\w+\\s*=\\s*[^;]+;|" +
        "\\b\\w+\\s*\\+\\s*\\w+|" +
        "\\b\\w+\\s*==\\s*\\w+|" +
        "\\b\\w+\\s*!=\\s*\\w+"
    );

    @Autowired
    public EvaluationService(OpenAIClient openAIClient) {
        this.openAIClient = openAIClient;
    }

    public EvaluationResponseDTO evaluate(EvaluationRequestDTO request) {
        logger.info("Evaluating approach for user: {}", request.getUsername());
        
        // Validate input
        String validationError = validateInput(request);
        if (validationError != null) {
            EvaluationResponseDTO responseDTO = new EvaluationResponseDTO();
            responseDTO.setCorrect(false);
            responseDTO.setFeedback(validationError);
            return responseDTO;
        }

        try {
            String aiResponse = openAIClient.getEvaluationFeedback(
                request.getProblemStatement(), 
                request.getUserApproach()
            );

            EvaluationResponseDTO responseDTO = new EvaluationResponseDTO();
            
            // Check if the response is exactly "Correct ✅"
            if (aiResponse != null && aiResponse.trim().equals("Correct ✅")) {
                responseDTO.setCorrect(true);
                responseDTO.setFeedback("Your approach is correct! 🎉");
            } else {
                responseDTO.setCorrect(false);
                responseDTO.setFeedback(aiResponse != null ? aiResponse : "Unable to evaluate the approach. Please try again.");
            }
            
            logger.info("Evaluation completed for user: {}", request.getUsername());
            return responseDTO;
            
        } catch (Exception e) {
            logger.error("Error during evaluation for user {}: {}", request.getUsername(), e.getMessage(), e);
            EvaluationResponseDTO responseDTO = new EvaluationResponseDTO();
            responseDTO.setCorrect(false);
            responseDTO.setFeedback("An error occurred during evaluation. Please try again later.");
            return responseDTO;
        }
    }

    public SolutionResponseDTO getSolutionSteps(SolutionRequestDTO request) {
        logger.info("Getting solution steps for user: {}", request.getUsername());
        
        if (request.getProblemStatement() == null || request.getProblemStatement().trim().isEmpty()) {
            SolutionResponseDTO responseDTO = new SolutionResponseDTO();
            responseDTO.setSteps("Problem statement is required.");
            return responseDTO;
        }

        try {
            String steps = openAIClient.getSolutionSteps(request.getProblemStatement());
            
            SolutionResponseDTO responseDTO = new SolutionResponseDTO();
            responseDTO.setSteps(steps != null ? steps : "Unable to provide solution steps. Please try again.");
            
            logger.info("Solution steps provided for user: {}", request.getUsername());
            return responseDTO;
            
        } catch (Exception e) {
            logger.error("Error getting solution steps for user {}: {}", request.getUsername(), e.getMessage(), e);
            SolutionResponseDTO responseDTO = new SolutionResponseDTO();
            responseDTO.setSteps("An error occurred while getting solution steps. Please try again later.");
            return responseDTO;
        }
    }

    private String validateInput(EvaluationRequestDTO request) {
        if (request.getProblemStatement() == null || request.getProblemStatement().trim().isEmpty()) {
            return "Problem statement is required.";
        }
        
        if (request.getUserApproach() == null || request.getUserApproach().trim().isEmpty()) {
            return "Your approach explanation is required.";
        }
        
        if (request.getUsername() == null || request.getUsername().trim().isEmpty()) {
            return "Username is required.";
        }
        
        // Check for code-like syntax in user approach
        if (containsCodeLikeSyntax(request.getUserApproach())) {
            return "Please explain your approach in plain English. Code or syntax is not allowed in explanations.";
        }
        
        // Check length limits
        if (request.getProblemStatement().length() > 2000) {
            return "Problem statement is too long. Please keep it under 2000 characters.";
        }
        
        if (request.getUserApproach().length() > 1000) {
            return "Your approach explanation is too long. Please keep it under 1000 characters.";
        }
        
        return null; // No validation errors
    }

    private boolean containsCodeLikeSyntax(String text) {
        return CODE_PATTERN.matcher(text).find();
    }
}
