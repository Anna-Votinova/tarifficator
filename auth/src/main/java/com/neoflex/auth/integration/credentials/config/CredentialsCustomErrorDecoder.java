package com.neoflex.auth.integration.credentials.config;

import feign.Response;
import feign.codec.ErrorDecoder;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class CredentialsCustomErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String methodKey, Response response) {

        if (HttpStatus.NOT_FOUND.value() == (response.status())) {
            return new UsernameNotFoundException("Credentials - клиент не найден");
        } else {
            return new Exception("Credentials");
        }
    }
}
