package com.neoflex.credentials.dto.error;

public record ErrorResponse(
        String error,
        String message
) {}
