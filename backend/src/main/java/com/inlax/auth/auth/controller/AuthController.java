package com.inlax.auth.auth.controller;

import com.inlax.auth.auth.dto.LoginRequest;
import com.inlax.auth.auth.dto.LoginResponse;
import com.inlax.auth.auth.dto.RegisterRequest;
import com.inlax.auth.auth.dto.RegisterResponse;
import com.inlax.auth.auth.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public RegisterResponse register(
            @Valid @RequestBody RegisterRequest request
    ){
        return authService.register(request);
    }

    @PostMapping("/login")
    public LoginResponse login(
            @Valid @RequestBody LoginRequest request
            ){
        return authService.login(request);
    }
}
