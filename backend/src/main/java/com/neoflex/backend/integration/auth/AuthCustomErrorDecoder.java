package com.neoflex.backend.integration.auth;

import com.neoflex.backend.exception.AccessException;
import com.neoflex.backend.exception.AuthorizationException;
import com.neoflex.backend.exception.BadRequestException;
import com.neoflex.backend.exception.auth.UserNotFoundException;
import feign.Response;
import feign.codec.ErrorDecoder;

public class AuthCustomErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String methodKey, Response response) {

        return switch (response.status()) {
            case 400 -> new BadRequestException("Auth - проверка данных не пройдена");
            case 403 -> new AccessException("Auth - доступ запрещен");
            case 404 -> new UserNotFoundException("Auth - юзер не найден");
            case 409 -> new AuthorizationException("Auth - токен не прошел проверку");
            default -> new Exception("Tariff");
        };
    }
}
