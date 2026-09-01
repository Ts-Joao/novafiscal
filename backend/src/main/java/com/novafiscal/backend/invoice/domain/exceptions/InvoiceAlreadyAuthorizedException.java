package com.novafiscal.backend.invoice.domain.exceptions;

public class InvoiceAlreadyAuthorizedException extends RuntimeException {

    public InvoiceAlreadyAuthorizedException(String message) {
        super(message);
    }
}
