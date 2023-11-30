package com.neoflex.backend.integration.credentials;

import com.neoflex.backend.exception.BadRequestException;
import com.neoflex.backend.exception.credentials.ApplicationNotSupportedException;
import com.neoflex.backend.exception.credentials.ClientNotFoundException;
import com.neoflex.backend.exception.credentials.InvalidCredentialsException;
import feign.Response;
import feign.codec.ErrorDecoder;

public class CredentialsCustomErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String methodKey, Response response) {

        return switch (response.status()) {
            case 400 -> new BadRequestException("Credentials - проверка данных не пройдена");
            case 403 -> new ApplicationNotSupportedException("Credentials - данное приложение пока не поддерживается");
            case 404 -> new ClientNotFoundException("Credentials - клиент не найден");
            case 409 -> new InvalidCredentialsException("Credentials - данные клиента не валидны");
            default -> new Exception("Credentials");
        };
    }
}
