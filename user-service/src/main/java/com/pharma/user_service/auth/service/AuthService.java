package com.pharma.user_service.auth.service;

import com.pharma.user_service.auth.dto.AuthResponse;
import com.pharma.user_service.auth.dto.LoginRequest;
import com.pharma.user_service.config.JwtTokenProvider;
import com.pharma.user_service.exception.InvalidCredentialsException;
import com.pharma.user_service.user.entity.User;
import com.pharma.user_service.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthResponse login(LoginRequest request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new InvalidCredentialsException("Invalid email or password"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new InvalidCredentialsException("Invalid email or password");
        }

        String token = jwtTokenProvider.generateToken(user);

        return AuthResponse.builder()
                .token(token)
                .role(user.getRole())
                .email(user.getEmail())
                .build();
    }
}




