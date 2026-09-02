package com.novafiscal.backend.invoice.domain.model;

import com.novafiscal.backend.customer.domain.exception.InvalidDocumentException;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Getter
@SuperBuilder(toBuilder = true)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class NFCeInvoice extends Invoice {

    private String consumerCpf;
    private PaymentMethod paymentMethod;
    private BigDecimal changeAmount;

    public static NFCeInvoice create(UUID customerId, UUID purchaseId, BigDecimal totalAmount, String consumerCpf,
                                    PaymentMethod paymentMethod, BigDecimal changeAmount) {

        NFCeInvoice invoice = NFCeInvoice.builder()
                .id(UUID.randomUUID())
                .customerId(customerId)
                .purchaseId(purchaseId)
                .status(InvoiceStatus.PENDING)
                .totalAmount(totalAmount)
                .paymentMethod(paymentMethod)
                .changeAmount(changeAmount)
                .consumerCpf(consumerCpf)
                .build();

        invoice.validateCpf(consumerCpf);

        return invoice;
    }

    public static NFCeInvoice reconstitute(UUID id, UUID customerId, UUID purchaseId, InvoiceStatus status,
                                          BigDecimal totalAmount, String protocolNumber, String accessKey,
                                          PaymentMethod paymentMethod, BigDecimal changeAmount, String consumerCpf,
                                          Instant issuedAt, Instant authorizedAt, Instant canceledAt) {

        return NFCeInvoice.builder()
                .id(id)
                .customerId(customerId)
                .purchaseId(purchaseId)
                .status(status)
                .totalAmount(totalAmount)
                .protocolNumber(protocolNumber)
                .accessKey(accessKey)
                .paymentMethod(paymentMethod)
                .changeAmount(changeAmount)
                .consumerCpf(consumerCpf)
                .issuedAt(issuedAt)
                .authorizedAt(authorizedAt)
                .canceledAt(canceledAt)
                .build();
    }

    private void validateCpf(String cpf) {
        if (cpf == null || cpf.isBlank()) {
            throw new InvalidDocumentException("Invalid CPF number");
        }

        String cleanCpf = cpf.replaceAll("\\D", "");

        if (!cleanCpf.matches("^(?!(\\d)\\1{10}$)\\d{11}$")) {
            throw new InvalidDocumentException("Invalid customer CPF");
        }
    }
}
