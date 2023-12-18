package com.neoflex.auth.dto;

import lombok.Data;
import lombok.ToString;

@Data
public class ClientSecurityDto {
        private Long id;
        private String login;
        @ToString.Exclude
        private String password;
        private String role;
}
