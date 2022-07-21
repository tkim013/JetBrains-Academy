package com.example.cinema.exceptionhandling;

public class NumberOutOfBoundsException extends RuntimeException{
    public NumberOutOfBoundsException(String msg) {
        super(msg);
    }
}
