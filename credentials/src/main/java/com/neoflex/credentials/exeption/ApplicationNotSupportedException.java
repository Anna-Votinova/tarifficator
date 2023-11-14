package com.neoflex.credentials.exeption;

public class ApplicationNotSupportedException extends RuntimeException {
    public ApplicationNotSupportedException(String message) {
        super(message);
    }
}
