package com.neoflex.auth.dto.error;

public record Violation(String fieldName, String message) {
}
