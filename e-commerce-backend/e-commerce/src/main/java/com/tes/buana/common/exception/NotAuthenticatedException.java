package com.tes.buana.common.exception;

public class NotAuthenticatedException extends Exception{
    private String message;

    public NotAuthenticatedException(String message) {
        super(message);
        this.message = message;
    }
}
