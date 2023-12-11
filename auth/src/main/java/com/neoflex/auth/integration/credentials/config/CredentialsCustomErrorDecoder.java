package com.neoflex.auth.integration.credentials.config;

import com.neoflex.auth.exception.ClientNotFoundException;
import feign.Response;
import feign.codec.ErrorDecoder;
import org.springframework.http.HttpStatus;

public class CredentialsCustomErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String methodKey, Response response) {

        if (HttpStatus.NOT_FOUND.value() == (response.status())) {
            return new ClientNotFoundException("Credentials - клиент не найден");
        } else {
            return new Exception("Credentials");
        }
    }
}
