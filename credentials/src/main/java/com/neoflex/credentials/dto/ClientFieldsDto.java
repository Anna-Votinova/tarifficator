package com.neoflex.credentials.dto;

/**
 * Fields for search credentials
 */
public record ClientFieldsDto(
        String lastname,
        String firstname,
        String middleName,
        String phoneNumber,
        String email
) {}
