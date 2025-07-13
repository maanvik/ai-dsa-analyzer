package com.ai.dsa_analyzer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.ai.dsa_analyzer.dto.EvaluationRequestDTO;
import com.ai.dsa_analyzer.dto.EvaluationResponseDTO;
import com.ai.dsa_analyzer.dto.SolutionRequestDTO;
import com.ai.dsa_analyzer.dto.SolutionResponseDTO;
import com.ai.dsa_analyzer.service.EvaluationService;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class EvaluationController {

    private final EvaluationService evaluationService;

    @Autowired
    public EvaluationController(EvaluationService evaluationService) {
        this.evaluationService = evaluationService;
    }

    @PostMapping("/evaluate")
    public EvaluationResponseDTO evaluate(@RequestBody EvaluationRequestDTO requestDTO) {
        return evaluationService.evaluate(requestDTO);
    }

    @PostMapping("/solution")
    public SolutionResponseDTO getSolutionSteps(@RequestBody SolutionRequestDTO requestDTO) {
        return evaluationService.getSolutionSteps(requestDTO);
    }
}
