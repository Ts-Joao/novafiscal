package com.novafiscal.backend.invoice.domain.model;

import com.novafiscal.backend.invoice.domain.exceptions.InvoiceAlreadyAuthorizedException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@SuperBuilder(toBuilder = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

    public void submit() {
        if (status != InvoiceStatus.PENDING) {
            throw new IllegalStateException("Only pending invoices can be submitted");
        }
        this.status = InvoiceStatus.SUBMITTED;
    }

    public void authorize(String protocolNumber, String accessKey) {
        if (status == InvoiceStatus.AUTHORIZED) {
            throw new InvoiceAlreadyAuthorizedException("Invoice already authorized");
        }

        if (status != InvoiceStatus.SUBMITTED) {
            throw new IllegalStateException("Only submitted invoices can be authorized");
        }

        this.status = InvoiceStatus.AUTHORIZED;
        this.protocolNumber = protocolNumber;
        this.accessKey = accessKey;
        this.authorizedAt = Instant.now();
    }

    public void reject() {
        if (status != InvoiceStatus.SUBMITTED) {
            throw new IllegalStateException("Only submitted invoices can be rejected");
        }
        this.status = InvoiceStatus.REJECTED;
    }

    public void cancel() {
        if (status != InvoiceStatus.AUTHORIZED) {
            throw new IllegalStateException("Only authorized invoices can be cancelled");
        }
        this.status = InvoiceStatus.CANCELED;
        this.canceledAt = Instant.now();
    }
}
