package com.tes.buana.common.exception;

import com.tes.buana.entity.Product;

public class NotEnoughProductException extends Exception {

    private static final String DEFAULT_MESSAGE = "Not enough products in stock";

    public NotEnoughProductException() {
        super(DEFAULT_MESSAGE);
    }

    public NotEnoughProductException(Product product) {
        super(String.format("Not enough %s products in stock. Only %d left", product.getName(), product.getQuantityInStock()));
    }
}