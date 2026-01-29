package com.sopheak.istadfinalems.utils;
import lombok.Builder;
import java.util.Date;

@Builder
public record ResponseTemplate<T>(
        String staus,
        Date date,
        String message,
        T data
){}

