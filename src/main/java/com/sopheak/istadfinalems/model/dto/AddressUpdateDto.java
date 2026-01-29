package com.sopheak.istadfinalems.model.dto;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record AddressUpdateDto(
        @NotBlank(message = "Street is required if updating")
        String street,
        @NotBlank(message = "City is required if updating")
        String city,
        @NotBlank(message = "province is required if updating")
        String province,
        @NotBlank(message = "Country is required if updating")
        String country
) {}