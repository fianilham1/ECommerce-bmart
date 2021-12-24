package com.tes.buana.common.exception;

public class NotFoundException extends Exception {
    public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException(String entityName, String id) {
        super(String.format("Unable to found %s with ID: %s", entityName, id));
    }
}
