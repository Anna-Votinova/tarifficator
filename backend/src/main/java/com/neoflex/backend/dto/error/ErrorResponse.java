package com.neoflex.backend.dto.error;

public record ErrorResponse(
        String error,
        String message
) {}
