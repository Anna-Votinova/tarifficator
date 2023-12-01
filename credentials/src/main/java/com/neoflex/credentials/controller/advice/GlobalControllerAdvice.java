package com.neoflex.credentials.controller.advice;

import com.neoflex.credentials.dto.error.ErrorResponse;
import com.neoflex.credentials.dto.error.ValidationErrorResponse;
import com.neoflex.credentials.dto.error.Violation;
import com.neoflex.credentials.exeption.*;
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

    @ExceptionHandler(Throwable.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleThrowable(Throwable e) {
        log.error(e.getMessage(), e);
        return new ErrorResponse("Непредвиденная ошибка: ", e.getMessage());
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
        return new ErrorResponse("Ошибка введенных данных: ", e.getMessage());
    }

    @ExceptionHandler(ApplicationNotSupportedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorResponse handleApplicationNotSupportedException(ApplicationNotSupportedException e) {
        log.error(e.getMessage(), e);
        return new ErrorResponse("Ошибка введенных данных: ", e.getMessage());
    }

    @ExceptionHandler(ValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleValidationException(ValidationException e) {
        log.error(e.getMessage(), e);
        return new ErrorResponse("Ошибка валидации: ", e.getMessage());
    }

    @ExceptionHandler(QueryOperatorNotSupportedException.class)
    @ResponseStatus(HttpStatus.NOT_IMPLEMENTED)
    public ErrorResponse handleCriteriaOperatorNotSupportedException(QueryOperatorNotSupportedException e) {
        log.error(e.getMessage(), e);
        return new ErrorResponse("Ошибка валидации оператора запроса: ", e.getMessage());
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
