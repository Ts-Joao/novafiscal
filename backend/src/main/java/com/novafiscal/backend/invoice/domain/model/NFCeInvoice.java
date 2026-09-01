package com.novafiscal.backend.invoice.domain.model;

import com.novafiscal.backend.customer.domain.model.Document;

import java.math.BigDecimal;

public final class NFCeInvoice extends Invoice {
    private String customerStateRegistration;
    private Document customerDocument;
    private String operationNumber;
    private String cfop;
    private BigDecimal icmsAmount;
}
