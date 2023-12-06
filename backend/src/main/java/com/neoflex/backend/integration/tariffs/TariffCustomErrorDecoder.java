package com.neoflex.backend.integration.tariffs;

import com.neoflex.backend.exception.BadRequestException;
import com.neoflex.backend.exception.product.RevisionException;
import com.neoflex.backend.exception.tariffs.TariffNotFoundException;
import feign.Response;
import feign.codec.ErrorDecoder;

public class TariffCustomErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String methodKey, Response response) {

        return switch (response.status()) {
            case 400 -> new BadRequestException("Tariff - проверка данных не пройдена");
            case 404 -> new TariffNotFoundException("Tariff - тариф не найден");
            case 409 -> new RevisionException("Tariff - данные для получения ревизии некорректны");
            default -> new Exception("Tariff");
        };
    }
}
