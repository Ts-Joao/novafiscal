package com.novafiscal.backend.customer.domain.exception;

public class DuplicatedCustomerException extends RuntimeException {

    public DuplicatedCustomerException(String message) {
        super(message);
    }
}
