package com.sopheak.istadfinalems.authService;
import lombok.Builder;

@Builder
public record ResetPasswordDto(
        String email,
        String userId,
        String newPassword,
        String confirmPassword
) {}
