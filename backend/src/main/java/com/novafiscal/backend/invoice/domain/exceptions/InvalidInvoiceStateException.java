package com.novafiscal.backend.invoice.domain.exceptions;

public class InvalidInvoiceStateException extends RuntimeException {

    public InvalidInvoiceStateException(String message) {
        super(message);
    }
}
