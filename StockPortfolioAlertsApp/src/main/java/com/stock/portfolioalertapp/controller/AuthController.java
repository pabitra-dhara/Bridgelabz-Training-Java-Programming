package com.stock.portfolioalertapp.controller;

import com.stock.portfolioalertapp.dto.AuthResponse;
import com.stock.portfolioalertapp.dto.LoginRequest;
import com.stock.portfolioalertapp.dto.RegisterRequest;
import com.stock.portfolioalertapp.dto.UserResponse;
import com.stock.portfolioalertapp.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public UserResponse register(@RequestBody RegisterRequest request) {
        return authService.register(request);
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody LoginRequest request) {
        return authService.login(request);
    }
}
