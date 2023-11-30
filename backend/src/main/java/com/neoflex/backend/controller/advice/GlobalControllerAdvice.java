package com.neoflex.backend.controller.advice;

import com.neoflex.backend.dto.error.ErrorResponse;
import com.neoflex.backend.dto.error.ValidationErrorResponse;
import com.neoflex.backend.dto.error.Violation;
import com.neoflex.backend.exception.BadRequestException;
import com.neoflex.backend.exception.credentials.ApplicationNotSupportedException;
import com.neoflex.backend.exception.credentials.ClientNotFoundException;
import com.neoflex.backend.exception.credentials.InvalidCredentialsException;
import com.neoflex.backend.exception.product.ProductNotFoundException;
import com.neoflex.backend.exception.product.RevisionException;
import com.neoflex.backend.exception.tariffs.TariffNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;
import java.util.List;

@Slf4j
@RestControllerAdvice
public class GlobalControllerAdvice {

    private static final String RECEIVED_DATA_ERROR_MESSAGE = "Ошибка введенных данных: ";

    @ExceptionHandler(Throwable.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleThrowable(Throwable e) {
        log.error(e.getMessage(), e);
        return new ErrorResponse("Непредвиденная ошибка: ", e.getMessage());
    }

    @ExceptionHandler(TariffNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleProductNotFoundException(TariffNotFoundException e) {
        log.error(e.getMessage(), e);
        return new ErrorResponse(RECEIVED_DATA_ERROR_MESSAGE, e.getMessage());
    }

    @ExceptionHandler(ProductNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleProductNotFoundException(ProductNotFoundException e) {
        log.error(e.getMessage(), e);
        return new ErrorResponse(RECEIVED_DATA_ERROR_MESSAGE, e.getMessage());
    }

    @ExceptionHandler(RevisionException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handleRevisionException(RevisionException e) {
        log.error(e.getMessage(), e);
        return new ErrorResponse("Ошибка при обращении в базу данных: ", e.getMessage());
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handleInvalidCredentialsException(InvalidCredentialsException e) {
        log.error(e.getMessage(), e);
        return new ErrorResponse("Ошибка валидации: ", e.getMessage());
    }

    @ExceptionHandler(ClientNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleClientNotFoundException(ClientNotFoundException e) {
        log.error(e.getMessage(), e);
        return new ErrorResponse(RECEIVED_DATA_ERROR_MESSAGE, e.getMessage());
    }

    @ExceptionHandler(ApplicationNotSupportedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorResponse handleApplicationNotSupportedException(ApplicationNotSupportedException e) {
        log.error(e.getMessage(), e);
        return new ErrorResponse(RECEIVED_DATA_ERROR_MESSAGE, e.getMessage());
    }

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleBadRequestException(BadRequestException e) {
        log.error(e.getMessage(), e);
        return new ErrorResponse("Ошибка валидации: ", e.getMessage());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ValidationErrorResponse onConstraintValidationException(ConstraintViolationException e) {
        log.error(e.getMessage(), e);
        List<Violation> violations = e.getConstraintViolations().stream()
                .map(violation -> new Violation(violation.getPropertyPath().toString(),
                        violation.getMessage()))
                .toList();
        return new ValidationErrorResponse(violations);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ValidationErrorResponse onMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error(e.getMessage(), e);
        List<Violation> violations = e.getBindingResult().getFieldErrors().stream()
                .map(error -> new Violation(error.getField(), error.getDefaultMessage()))
                .toList();
        return new ValidationErrorResponse(violations);
    }
}
