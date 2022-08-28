package com.example.AntiFraudSystem.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ExceptionHandlerControllerAdvice {

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public @ResponseBody ExceptionResponse handleBadRequestException(final Exception exception,
                                                                            final HttpServletRequest request) {

        ExceptionResponse error = new ExceptionResponse();
        error.setError(exception.getMessage());
        return error;
    }

    @ExceptionHandler(ConflictException.class)
    @ResponseStatus(value = HttpStatus.CONFLICT)
    public @ResponseBody ExceptionResponse handleConflictException(final Exception exception,
                                                                            final HttpServletRequest request) {

        ExceptionResponse error = new ExceptionResponse();
        error.setError(exception.getMessage());
        return error;
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public @ResponseBody ExceptionResponse handleNotFoundException(final Exception exception,
                                                                   final HttpServletRequest request) {

        ExceptionResponse error = new ExceptionResponse();
        error.setError(exception.getMessage());
        return error;
    }

    @ExceptionHandler(UnprocessableEntityException.class)
    @ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)
    public @ResponseBody ExceptionResponse handleUnprocessableEntityException(final Exception exception,
                                                                   final HttpServletRequest request) {

        ExceptionResponse error = new ExceptionResponse();
        error.setError(exception.getMessage());
        return error;
    }
}
