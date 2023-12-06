package com.neoflex.backend.exception.credentials;

public class ApplicationNotSupportedException extends RuntimeException {
    public ApplicationNotSupportedException(String message) {
        super(message);
    }
}
