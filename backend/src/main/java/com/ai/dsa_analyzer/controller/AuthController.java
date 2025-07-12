package com.ai.dsa_analyzer.controller;

import com.ai.dsa_analyzer.dto.AuthResponse;
import com.ai.dsa_analyzer.dto.LoginRequest;
import com.ai.dsa_analyzer.dto.SignupRequest;
import com.ai.dsa_analyzer.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/signup")
    public AuthResponse signup(@RequestBody SignupRequest request) {
        return authService.signup(request);
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody LoginRequest request) {
        return authService.login(request);
    }
}
