package com.sopheak.istadfinalems.utils;
import lombok.Builder;
import java.time.LocalDateTime;

@Builder
public record AIPErrorResponse(
        String status,
        LocalDateTime timeStamp,
        String errorMessage
){}
