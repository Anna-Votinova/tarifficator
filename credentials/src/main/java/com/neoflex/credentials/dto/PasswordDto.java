package com.neoflex.credentials.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Пароль")
public record PasswordDto(String password) {}
