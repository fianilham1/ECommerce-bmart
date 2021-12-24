package com.tes.buana.common.exception;

public class BusinessException extends Exception {
    private String message;
    public BusinessException() {
        super();
    }

    public BusinessException(String message) {
        super(message);
        this.message = message;
    }
}
