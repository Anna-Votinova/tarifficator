package com.neoflex.auth.service;

import com.neoflex.auth.dto.*;
import com.neoflex.auth.exception.ClientNotFoundException;
import com.neoflex.auth.integration.credentials.config.CredentialsClient;
import com.neoflex.auth.mapper.UserMapper;
import com.neoflex.auth.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final JWTProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;
    private final CredentialsClient credentialsClient;
    private final AuthenticationManager authenticationManager;

    public ClientResponseDto register(String applicationType, ClientRequestDto clientRequestDto) {
        String encodedPassword = passwordEncoder.encode(clientRequestDto.getPassword());
        clientRequestDto.setPassword(encodedPassword);       
        return credentialsClient.createClient(applicationType, clientRequestDto);
    }

    public AccessToken authenticate(LoginRequest loginRequest) {
        User user;
        ClientSecurityDto clientSecurityDto;

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getLogin(), loginRequest.getPassword()));

        try {
           clientSecurityDto = credentialsClient.getClientByLogin(loginRequest.getLogin());
        } catch (UsernameNotFoundException e) {
            throw new ClientNotFoundException("Юзер с логином " + loginRequest.getLogin() + " не найден");
        }

        user = UserMapper.toUser(clientSecurityDto);
        String jwtToken = jwtProvider.generateToken(user);
        return new AccessToken(jwtToken);
    }

    public String getLogin() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        log.info("Load user from security context {}", user);
        return user.getLogin();
    }
}
