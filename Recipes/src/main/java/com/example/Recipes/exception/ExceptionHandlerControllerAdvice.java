package com.example.Recipes.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ExceptionHandlerControllerAdvice {

    @ExceptionHandler(IdNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public @ResponseBody ExceptionResponse handleIdNotFoundException(final Exception exception,
                                                                            final HttpServletRequest request) {

        ExceptionResponse error = new ExceptionResponse();
        error.setError(exception.getMessage());
        return error;
    }

    @ExceptionHandler(QueryParameterException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public @ResponseBody ExceptionResponse handleQueryParameterException(final Exception exception,
                                                                     final HttpServletRequest request) {

        ExceptionResponse error = new ExceptionResponse();
        error.setError(exception.getMessage());
        return error;
    }

    @ExceptionHandler(EmailNotFoundException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public @ResponseBody ExceptionResponse handleEmailNotFoundException(final Exception exception,
                                                                         final HttpServletRequest request) {

        ExceptionResponse error = new ExceptionResponse();
        error.setError(exception.getMessage());
        return error;
    }

    @ExceptionHandler(UserExistsException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public @ResponseBody ExceptionResponse handleUserExistsException(final Exception exception,
                                                                        final HttpServletRequest request) {

        ExceptionResponse error = new ExceptionResponse();
        error.setError(exception.getMessage());
        return error;
    }

    @ExceptionHandler(AuthorizationException.class)
    @ResponseStatus(value = HttpStatus.FORBIDDEN)
    public @ResponseBody ExceptionResponse handleAuthorizationException(final Exception exception,
                                                                     final HttpServletRequest request) {

        ExceptionResponse error = new ExceptionResponse();
        error.setError(exception.getMessage());
        return error;
    }
}