package com.neoflex.backend.exception.tariffs;

public class TariffNotFoundException extends RuntimeException {
    public TariffNotFoundException(String message) {
        super(message);
    }
}
