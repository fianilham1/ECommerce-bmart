package com.tes.buana.common.exception;

public class BadRequestException extends Exception{
    private String message;

    public BadRequestException(String message) {
        super(message);
        this.message = message;
    }
}
