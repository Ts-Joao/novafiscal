package com.novafiscal.backend.invoice.domain.models;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public abstract sealed class Invoice permits NFCeInvoice, NFeInvoice {
    protected UUID id;
    protected UUID customerId;
    protected UUID purchaseId;
    protected InvoiceStatus status;
    protected BigDecimal totalAmount;
    protected String protocolNumber;
    protected String accessKey;
    protected Instant issuedAt;
    protected Instant authorizedAt;
    protected Instant canceledAt;
}
