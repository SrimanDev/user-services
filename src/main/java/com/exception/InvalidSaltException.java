package com.exception;

public class InvalidSaltException extends RuntimeException{

    public InvalidSaltException(String message) {
        super(message);
    }
}
