package com.inlax.auth.auth.controller;

import com.inlax.auth.auth.dto.*;
import com.inlax.auth.auth.service.AuthService;
import com.inlax.auth.auth.dto.RefreshTokenRequest;
import com.inlax.auth.auth.dto.LogoutRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/refresh")
    public LoginResponse refresh(
            @Valid @RequestBody RefreshTokenRequest request
    ){
        return authService.refreshToken(
                request.getRefreshToken()
        );
    }

    @GetMapping("/username")
    public UsernameResponse getUsername(
            Authentication authentication
    ) {

        return UsernameResponse.builder()
                .username(authentication.getName())
                .build();
    }

    @PostMapping("/logout")
    public void logout(
            @Valid @RequestBody LogoutRequest request
    ){
        authService.logout(request.getRefreshToken());
    }
}
