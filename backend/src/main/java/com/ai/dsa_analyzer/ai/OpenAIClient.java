package com.ai.dsa_analyzer.ai;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.completion.chat.ChatMessageRole;
import com.theokanning.openai.completion.chat.ChatCompletionResult;
import com.theokanning.openai.service.OpenAiService;

import java.util.Arrays;
import java.util.List;
import java.time.Duration;

@Component
public class OpenAIClient {

    private static final Logger logger = LoggerFactory.getLogger(OpenAIClient.class);
    private final OpenAiService openAiService;

    public OpenAIClient(@Value("${openai.api.key}") String apiKey) {
        if (apiKey == null || apiKey.trim().isEmpty()) {
            throw new IllegalArgumentException("OpenAI API key is required");
        }
        
        
        String maskedKey = apiKey.length() > 8 ? apiKey.substring(0, 8) + "..." : "***";
        logger.info("Initializing OpenAI client with API key: {}", maskedKey);
        
        this.openAiService = new OpenAiService(apiKey, Duration.ofSeconds(30));
        logger.info("OpenAI client initialized successfully");
    }

    public String getEvaluationFeedback(String problemStatement, String userApproach) {
        try {
            String prompt = buildEvaluationPrompt(problemStatement, userApproach);
            logger.debug("Sending evaluation request to OpenAI");
            
            List<ChatMessage> messages = Arrays.asList(
                    new ChatMessage(ChatMessageRole.SYSTEM.value(), buildSystemPrompt()),
                    new ChatMessage(ChatMessageRole.USER.value(), prompt)
            );

            ChatCompletionRequest request = ChatCompletionRequest.builder()
                    .model("gpt-4o-mini") 
                    .messages(messages)
                    .maxTokens(500)
                    .temperature(0.3)
                    .build();

            logger.debug("Sending request with model: {}", request.getModel());
            ChatCompletionResult response = openAiService.createChatCompletion(request);
            String content = response.getChoices().get(0).getMessage().getContent().trim();

            logger.debug("Received OpenAI response: {}", content);
            return content;
            
        } catch (Exception e) {
            logger.error("Error calling OpenAI API: {}", e.getMessage(), e);
            if (e.getMessage().contains("404")) {
                return "API endpoint not found. Please check your OpenAI API key and model access.";
            } else if (e.getMessage().contains("401")) {
                return "Unauthorized. Please check your OpenAI API key.";
            } else if (e.getMessage().contains("429")) {
                return "Rate limit exceeded. Please try again in a few seconds.";
            } else {
                return "Unable to evaluate your approach at this time. Please try again later.";
            }
        }
    }

    public String getSolutionSteps(String problemStatement) {
        try {
            logger.debug("Sending solution request to OpenAI");
            
            List<ChatMessage> messages = Arrays.asList(
                    new ChatMessage(ChatMessageRole.SYSTEM.value(), buildSolutionSystemPrompt()),
                    new ChatMessage(ChatMessageRole.USER.value(), 
                        "Problem: " + problemStatement + "\n\nProvide only the steps to solve this problem in plain English. Do not include any code or syntax.")
            );

            ChatCompletionRequest request = ChatCompletionRequest.builder()
                    .model("gpt-4o-mini") 
                    .messages(messages)
                    .maxTokens(800)
                    .temperature(0.2)
                    .build();

            logger.debug("Sending solution request with model: {}", request.getModel());
            ChatCompletionResult response = openAiService.createChatCompletion(request);
            String content = response.getChoices().get(0).getMessage().getContent().trim();

            logger.debug("Received solution response: {}", content);
            return content;
            
        } catch (Exception e) {
            logger.error("Error calling OpenAI API for solution: {}", e.getMessage(), e);
            if (e.getMessage().contains("404")) {
                return "API endpoint not found. Please check your OpenAI API key and model access.";
            } else if (e.getMessage().contains("401")) {
                return "Unauthorized. Please check your OpenAI API key.";
            } else if (e.getMessage().contains("429")) {
                return "Rate limit exceeded. Please try again in a few seconds.";
            } else {
                return "Unable to provide solution steps at this time. Please try again later.";
            }
        }
    }

    private String buildSystemPrompt() {
        return "You are an expert DSA tutor. Your task is to evaluate a student's approach to solving a DSA problem.\n\n" +
               "IMPORTANT RULES:\n" +
               "1. If the approach is correct, respond with exactly: 'Correct ✅'\n" +
               "2. If the approach is incorrect, provide ONE indirect hint that guides the student without giving away the solution\n" +
               "3. Do not provide code or syntax\n" +
               "4. Be encouraging and constructive\n" +
               "5. Focus on the logical approach, not implementation details";
    }

    private String buildSolutionSystemPrompt() {
        return "You are an expert DSA tutor. Your task is to provide solution steps for a DSA problem.\n\n" +
               "IMPORTANT RULES:\n" +
               "1. Provide ONLY plain-English steps to solve the problem\n" +
               "2. Do NOT include any code, syntax, or programming language elements\n" +
               "3. Focus on the logical approach and algorithm steps\n" +
               "4. Be clear and educational\n" +
               "5. Number your steps for clarity";
    }

    private String buildEvaluationPrompt(String problemStatement, String userApproach) {
        return String.format("Problem Statement: %s\n\nUser's Approach: %s\n\n" +
                           "Evaluate if the user's approach is correct. If correct, reply with exactly 'Correct ✅'. " +
                           "If not, provide one helpful hint to guide them in the right direction.",
                           problemStatement, userApproach);
    }
}
