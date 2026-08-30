package com.novafiscal.backend.customer.domain.exception;

public class CustomerAlreadyInactiveException extends RuntimeException {

    public CustomerAlreadyInactiveException(String message) {
        super(message);
    }
}
