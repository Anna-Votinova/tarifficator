package com.neoflex.auth.mapper;

import com.neoflex.auth.dto.ClientSecurityDto;
import com.neoflex.auth.model.User;
import com.neoflex.auth.model.enums.Role;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class UserMapper {

    public static User toUser(ClientSecurityDto clientSecurityDto) {
        return User.builder()
                .id(clientSecurityDto.getId())
                .login(clientSecurityDto.getLogin())
                .password(clientSecurityDto.getPassword())
                .role(toRole(clientSecurityDto.getRole()))
                .build();
    }

    public static Role toRole(String clientRole) {
        return switch (clientRole) {
            case "ROLE_ADMIN" -> Role.ADMIN;
            case "ROLE_SUPER_ADMIN" -> Role.SUPER_ADMIN;
            default -> Role.USER;
        };
    }
}
