package com.novafiscal.backend.invoice.domain.model;

import com.novafiscal.backend.customer.domain.exception.InvalidDocumentException;

import java.math.BigDecimal;

public final class NFCeInvoice extends Invoice {

    private String customerCpf;
    private PaymentMethod paymentMethod;
    private BigDecimal changeAmount;

    private void validateCpf( String cpf) {
        if (cpf == null || cpf.trim().isEmpty()) {
            throw new InvalidDocumentException("Invalid CPF number");
        }

        String regex = "^(\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}|\\d{11})$";
        if (!cpf.matches(regex)) {
            throw new InvalidDocumentException("Invalid customer CPF");
        }
    }
}
