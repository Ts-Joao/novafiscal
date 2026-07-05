package com.novafiscal.backend.customer.domain.exception;

public class InvalidDocumentException extends RuntimeException {

    public InvalidDocumentException(String message) {
        super(message);
    }
}
