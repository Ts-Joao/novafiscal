package com.novafiscal.backend.invoice.domain.model;

import com.novafiscal.backend.customer.domain.exception.InvalidDocumentException;
import com.novafiscal.backend.common.domain.model.Document;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Set;
import java.util.UUID;

@Getter
@SuperBuilder(toBuilder = true)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class NFeInvoice extends Invoice {

    private String customerStateRegistration;
    private Document customerDocument;
    private String operationNumber;
    private String cfop;
    private BigDecimal icmsAmount;

    public static NFeInvoice create(UUID customerId, UUID purchaseId, BigDecimal totalAmount,
                                      String customerStateRegistration, Document customerDocument,
                                      String operationNumber, String cfop, BigDecimal icmsAmount) {

        NFeInvoice invoice = NFeInvoice.builder()
                .id(UUID.randomUUID())
                .customerId(customerId)
                .purchaseId(purchaseId)
                .status(InvoiceStatus.PENDING)
                .totalAmount(totalAmount)
                .issuedAt(Instant.now())
                .customerStateRegistration(customerStateRegistration)
                .customerDocument(customerDocument)
                .operationNumber(operationNumber)
                .cfop(cfop)
                .icmsAmount(icmsAmount)
                .build();

        invoice.verifyState(customerStateRegistration);

        return invoice;
    }

    public static NFeInvoice reconstitute(UUID id, UUID customerId, UUID purchaseId, BigDecimal totalAmount,
                                            InvoiceStatus status, String customerStateRegistration,
                                            Document customerDocument, String operationNumber, String cfop,
                                            BigDecimal icmsAmount, Instant issuedAt, Instant authorizedAt,
                                            Instant canceledAt) {

        return NFeInvoice.builder()
                .id(id)
                .customerId(customerId)
                .purchaseId(purchaseId)
                .status(status)
                .totalAmount(totalAmount)
                .customerStateRegistration(customerStateRegistration)
                .customerDocument(customerDocument)
                .operationNumber(operationNumber)
                .cfop(cfop)
                .icmsAmount(icmsAmount)
                .issuedAt(issuedAt)
                .authorizedAt(authorizedAt)
                .canceledAt(canceledAt)
                .build();
    }

    private void verifyState(String customerStateRegistration) {
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
