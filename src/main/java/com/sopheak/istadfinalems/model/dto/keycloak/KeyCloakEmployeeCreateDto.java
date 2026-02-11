package com.sopheak.istadfinalems.model.dto.keycloak;
import lombok.Builder;

@Builder
public record KeyCloakEmployeeCreateDto(
        String firstName,
        String lastName,
        String email,
        String password
) {}
