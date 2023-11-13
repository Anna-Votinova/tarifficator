package com.neoflex.credentials.controller.advice;

import com.neoflex.credentials.dto.error.ErrorResponse;
import com.neoflex.credentials.exeption.InvalidCredentialsException;
import com.neoflex.credentials.exeption.NotCompletedImplementationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalControllerAdvice {

    @ExceptionHandler(Throwable.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleThrowable(Throwable e) {
        log.error(e.getMessage(), e);
        return new ErrorResponse("Непредвиденная ошибка: ", e.getMessage());
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleInvalidCredentialsException(InvalidCredentialsException e) {
        log.error(e.getMessage(), e);
        return new ErrorResponse("Ошибка введенных данных: ", e.getMessage());
    }

    @ExceptionHandler(NotCompletedImplementationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleNotCompletedComponentImplementation(NotCompletedImplementationException e) {
        log.error(e.getMessage(), e);
        return new ErrorResponse("Реализация не завершена: ", e.getMessage());
    }
}
