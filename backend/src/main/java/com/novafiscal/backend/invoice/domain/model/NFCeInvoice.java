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

    private String customerCpf;
    private PaymentMethod paymentMethod;
    private BigDecimal changeAmount;

    public static NFCeInvoice create(UUID customerId, UUID purchaseId, BigDecimal totalAmount, String customerCpf,
                                    PaymentMethod paymentMethod, BigDecimal changeAmount) {

        NFCeInvoice invoice = NFCeInvoice.builder()
                .id(UUID.randomUUID())
                .customerId(customerId)
                .purchaseId(purchaseId)
                .status(InvoiceStatus.PENDING)
                .totalAmount(totalAmount)
                .paymentMethod(paymentMethod)
                .changeAmount(changeAmount)
                .customerCpf(customerCpf)
                .build();

        invoice.validateCpf(customerCpf);

        return invoice;
    }

    public static NFCeInvoice reconstitute(UUID id, UUID customerId, UUID purchaseId, InvoiceStatus status,
                                          BigDecimal totalAmount, String protocolNumber, String accessKey,
                                          PaymentMethod paymentMethod, BigDecimal changeAmount, String customerCpf,
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
                .customerCpf(customerCpf)
                .issuedAt(issuedAt)
                .authorizedAt(authorizedAt)
                .canceledAt(canceledAt)
                .build();
    }

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
