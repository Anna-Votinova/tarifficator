package com.neoflex.auth.dto.error;

public record ErrorResponse(
        String error,
        String message
) {}
