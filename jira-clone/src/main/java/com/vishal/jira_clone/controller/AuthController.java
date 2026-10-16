package com.vishal.jira_clone.controller;

import com.vishal.jira_clone.dto.LoginRequestDTO;
import com.vishal.jira_clone.dto.LoginResponseDTO;
import com.vishal.jira_clone.dto.RegisterRequestDTO;
import com.vishal.jira_clone.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public String register(@RequestBody RegisterRequestDTO request) {
        return authService.register(request);
    }

    @PostMapping("/login")
    public LoginResponseDTO login(@RequestBody LoginRequestDTO request) {
        return authService.login(request);
    }
}