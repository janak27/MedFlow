package com.pharma.user_service.user.dto;

import com.pharma.user_service.role.entity.Role;
import com.pharma.user_service.user.entity.User;
import lombok.*;

@Data
@Builder
public class UserResponse {

    private Long id;
    private String email;
    private Role role;
    private boolean active;

    public static UserResponse fromEntity(User user) {
        return new UserResponse(
                user.getId(),
                user.getEmail(),
                user.getRole(),
                user.isActive()
        );
    }

}
