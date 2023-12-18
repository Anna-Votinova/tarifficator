package com.neoflex.product.controller.advice;

import com.neoflex.product.dto.error.ErrorResponse;
import com.neoflex.product.dto.error.ValidationErrorResponse;
import com.neoflex.product.dto.error.Violation;
import com.neoflex.product.exception.*;
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

    @ExceptionHandler(ProductNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleProductNotFoundException(ProductNotFoundException e) {
        log.error(e.getMessage(), e);
        return new ErrorResponse("Ошибка введенных данных: ", e.getMessage());
    }

    @ExceptionHandler(RevisionException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handleRevisionException(RevisionException e) {
        log.error(e.getMessage(), e);
        return new ErrorResponse("Ошибка при обращении в базу данных: ", e.getMessage());
    }

    @ExceptionHandler(ValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleValidationException(ValidationException e) {
        log.error(e.getMessage(), e);
        return new ErrorResponse("Ошибка введенных данных: ", e.getMessage());
    }

    @ExceptionHandler(AuthorizationException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handleAuthorizationException(AuthorizationException e) {
        log.error(e.getMessage(), e);
        return new ErrorResponse("Ошибка токена: ", e.getMessage());
    }

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleUserNotFoundException(UserNotFoundException e) {
        log.error(e.getMessage(), e);
        return new ErrorResponse("Ошибка валидации токена: ", e.getMessage());
    }

    @ExceptionHandler(AccessException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorResponse handleAccessException(AccessException e) {
        log.error(e.getMessage(), e);
        return new ErrorResponse("Ошибка валидации токена: ", e.getMessage());
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
