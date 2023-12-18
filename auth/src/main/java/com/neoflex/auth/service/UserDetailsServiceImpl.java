package com.neoflex.auth.service;

import com.neoflex.auth.dto.ClientSecurityDto;
import com.neoflex.auth.integration.credentials.config.CredentialsClient;
import com.neoflex.auth.mapper.UserMapper;
import com.neoflex.auth.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
@EnableMethodSecurity
public class UserDetailsServiceImpl implements UserDetailsService {

    private final CredentialsClient credentialsClient;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
            ClientSecurityDto clientSecurityDto = credentialsClient.getClientByLogin(login);
            User user = UserMapper.toUser(clientSecurityDto);
            log.info("User from database of service Credentials: {}", user);
            return user;
    }
}
