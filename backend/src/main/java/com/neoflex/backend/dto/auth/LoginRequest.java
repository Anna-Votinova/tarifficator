package com.neoflex.backend.dto.auth;

import lombok.Data;

@Data
public class LoginRequest {
    private String login;
    private String password;
}
