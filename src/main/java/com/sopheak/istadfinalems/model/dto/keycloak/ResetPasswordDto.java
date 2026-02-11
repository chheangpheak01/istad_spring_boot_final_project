package com.sopheak.istadfinalems.model.dto.keycloak;
import lombok.Builder;

@Builder
public record ResetPasswordDto(
        String email,
        String userId,
        String newPassword,
        String confirmPassword
) {}
