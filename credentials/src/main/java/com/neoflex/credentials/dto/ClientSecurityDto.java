package com.neoflex.credentials.dto;

import lombok.ToString;

/**
 * A client info for verification
 * @param id - user ID
 * @param login - an email, phone number or bank id, depending on the service used to register the user
 * @param password - encrypted user's password
 * @param role - user's role
 */
public record ClientSecurityDto(
        Long id,
        String login,
        @ToString.Exclude
        String password,
        String role
) {}
