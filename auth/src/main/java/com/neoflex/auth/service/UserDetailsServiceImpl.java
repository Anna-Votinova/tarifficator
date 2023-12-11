package com.neoflex.auth.service;

import com.neoflex.auth.exception.ClientNotFoundException;
import com.neoflex.auth.integration.credentials.config.CredentialsClient;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final CredentialsClient credentialsClient;

    @Override
    public UserDetails loadUserByUsername(String login) {
        try {
            //нужен ли маппер при получении дто из Креденшлс в ЮзерДетейлс
            return credentialsClient.getClientByLogin(login);
        } catch (ClientNotFoundException e) {
            throw new ClientNotFoundException("Юзер с логином " + login + " не найден");
        }
    }
}
