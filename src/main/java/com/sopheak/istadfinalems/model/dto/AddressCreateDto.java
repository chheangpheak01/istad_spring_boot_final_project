package com.sopheak.istadfinalems.model.dto;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record AddressCreateDto(
        @NotBlank(message = "Street is required")
        String street,
        @NotBlank(message = "City is required")
        String city,
        @NotBlank(message = "Province is required")
        String province,
        @NotBlank(message = "Country is required")
        String country
) {}
