package com.novafiscal.backend.invoice.domain.model;

import java.math.BigDecimal;

public final class NFeInvoice extends Invoice {
    private String customerCpf;
    private PaymentMethod paymentMethod;
    private BigDecimal changeAmount;
}
