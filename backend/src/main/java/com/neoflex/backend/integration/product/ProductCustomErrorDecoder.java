package com.neoflex.backend.integration.product;

import com.neoflex.backend.exception.BadRequestException;
import com.neoflex.backend.exception.product.ProductNotFoundException;
import com.neoflex.backend.exception.product.RevisionException;
import feign.Response;
import feign.codec.ErrorDecoder;

public class ProductCustomErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String methodKey, Response response) {

        return switch (response.status()) {
            case 400 -> new BadRequestException("Product - проверка данных не пройдена");
            case 404 -> new ProductNotFoundException("Product - продукт не найден");
            case 409 -> new RevisionException("Product - данные для получения ревизии некорректны");
            default -> new Exception("Product");
        };
    }
}
