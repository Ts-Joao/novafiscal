package com.novafiscal.backend.invoice.domain.model;

import com.novafiscal.backend.invoice.domain.exceptions.InvoiceAlreadyAuthorizedException;
import lombok.AccessLevel;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Builder(toBuilder = true, access = AccessLevel.PACKAGE)
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

    protected static Invoice create(UUID id, UUID customerId, UUID purchaseId, BigDecimal totalAmount) {
        Invoice invoice = Invoice.builder()
                .id(id)
                .customerId(customerId)
                .purchaseId(purchaseId)
                .status(InvoiceStatus.PENDING)
                .totalAmount(totalAmount)
                .build();

        return invoice;
    }

    protected static Invoice reconstitute(UUID id, UUID customerId, UUID purchaseId, InvoiceStatus status, BigDecimal totalAmount,
                                            String protocolNumber, String accessKey, Instant issuedAt, Instant authorizedAt, Instant canceledAt) {
        Invoice invoice = Invoice.builder()
                .id(id)
                .customerId(customerId)
                .purchaseId(purchaseId)
                .status(status)
                .totalAmount(totalAmount)
                .protocolNumber(protocolNumber)
                .accessKey(accessKey)
                .issuedAt(issuedAt)
                .authorizedAt(authorizedAt)
                .canceledAt(canceledAt)
                .build();

        return invoice;
    }

    private void submit() {
        if (status != InvoiceStatus.PENDING) {
            throw new IllegalStateException("Only pending invoices can be submitted");
        }
        this.status = InvoiceStatus.SUBMITTED;
    }

    private void authorize(String protocolNumber, String accessKey) {
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

    private void reject() {
        if (status != InvoiceStatus.SUBMITTED) {
            throw new IllegalStateException("Only submitted invoices can be rejected");
        }
        this.status = InvoiceStatus.REJECTED;
    }

    private void cancel() {
        if (status != InvoiceStatus.AUTHORIZED) {
            throw new IllegalStateException("Only authorized invoices can be cancelled");
        }
        this.status = InvoiceStatus.CANCELED;
        this.canceledAt = Instant.now();
    }
}
