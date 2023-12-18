package com.neoflex.backend.integration.tariffs;

import com.neoflex.backend.exception.AccessException;
import com.neoflex.backend.exception.AuthorizationException;
import com.neoflex.backend.exception.BadRequestException;
import com.neoflex.backend.exception.tariffs.TariffNotFoundException;
import feign.Response;
import feign.codec.ErrorDecoder;

public class TariffCustomErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String methodKey, Response response) {

        return switch (response.status()) {
            case 400 -> new BadRequestException("Tariff - проверка данных не пройдена");
            case 403 -> new AccessException("Auth - доступ запрещен");
            case 404 -> new TariffNotFoundException("Tariff - тариф или юзер не найдены");
            case 409 -> new AuthorizationException("Tariff - данные токена некорректны");
            default -> new Exception("Tariff");
        };
    }
}
