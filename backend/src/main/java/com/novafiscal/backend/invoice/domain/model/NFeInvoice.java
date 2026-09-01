package com.novafiscal.backend.invoice.domain.model;

import com.novafiscal.backend.customer.domain.exception.InvalidDocumentException;
import com.novafiscal.backend.customer.domain.model.Document;

import java.math.BigDecimal;
import java.util.Set;

public final class NFeInvoice extends Invoice {

    private String customerStateRegistration;
    private Document customerDocument;
    private String operationNumber;
    private String cfop;
    private BigDecimal icmsAmount;

    private void verifyState() {
        final Set<String> states = Set.of(
                "AC", "AL", "AM", "AP", "BA", "CE", "DF", "ES", "GO",
                "MA", "MG", "MS", "MT", "PA", "PB", "PE", "PI", "PR",
                "RJ", "RN", "RO", "RR", "RS", "SC", "SE", "SP", "TO"
        );

        if (customerStateRegistration == null) {
            throw new InvalidDocumentException("State is required");
        }

        if (!states.contains(customerStateRegistration.trim().toUpperCase())) {
            throw new InvalidDocumentException("State is not valid");
        }
    }
}
