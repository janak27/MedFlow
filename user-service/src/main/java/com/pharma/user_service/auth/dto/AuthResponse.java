package com.pharma.user_service.auth.dto;

import com.pharma.user_service.role.entity.Role;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthResponse {

    private String token;
    private Role role;
    private String email;
}
