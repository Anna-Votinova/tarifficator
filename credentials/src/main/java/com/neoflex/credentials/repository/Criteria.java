package com.neoflex.credentials.repository;

public record Criteria(
        String field,
        QueryOperator operator,
        Object value
) {}