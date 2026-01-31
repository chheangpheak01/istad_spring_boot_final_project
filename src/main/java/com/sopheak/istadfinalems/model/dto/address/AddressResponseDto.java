package com.sopheak.istadfinalems.model.dto.address;
import lombok.Builder;

@Builder
public record AddressResponseDto(
        String street,
        String city,
        String province,
        String country
) {}
