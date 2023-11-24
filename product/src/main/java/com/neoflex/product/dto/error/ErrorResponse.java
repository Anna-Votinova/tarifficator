package com.neoflex.product.dto.error;

public record ErrorResponse(
        String error,
        String message
) {}
