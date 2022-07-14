package com.example.Recipes.exception;

public class UserExistsException extends RuntimeException {

    public UserExistsException(String msg) {
        super(msg);
    }
}
