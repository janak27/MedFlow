package com.pharma.user_service.auth.controller;

import com.pharma.user_service.auth.dto.AuthResponse;
import com.pharma.user_service.auth.dto.LoginRequest;
import com.pharma.user_service.auth.service.AuthService;
import com.pharma.user_service.config.JwtTokenProvider;
import com.pharma.user_service.role.entity.Role;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final JwtTokenProvider jwtTokenProvider; // inject JWT provider

    @PostMapping("/login")
    public AuthResponse login(@Valid @RequestBody LoginRequest request) {
        return authService.login(request);
    }

    // -------------------- /auth/me --------------------
    @GetMapping("/me")
    public AuthResponse getCurrentUser(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.substring(7); // remove "Bearer "
        String email = jwtTokenProvider.getEmailFromToken(token);
        String role = jwtTokenProvider.getRoleFromToken(token);

        return AuthResponse.builder()
                .email(email)
                .role(Role.valueOf(role))
                .build();
    }
}


