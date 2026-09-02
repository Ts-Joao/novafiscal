package com.novafiscal.backend.invoice.infrastructure.persistence;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

@Entity
@Getter
@Table(name = "nfe_invoice")
@SuperBuilder()
@DiscriminatorValue("NFE")
@PrimaryKeyJoinColumn(name = "id")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NFeInvoiceJpaEntity extends InvoiceJpaEntity {

    @Column(name = "customer_state_registration", nullable = false)
    private String customerStateRegistration;

    @Column(name = "customer_document_type", nullable = false)
    private String customerDocumentType;

    @Column(name = "customer_document_number", nullable = false)
    private String customerDocumentNumber;

    @Column(name = "operation_number", nullable = false)
    private String operationNumber;

    @Column(name = "cfop", nullable = false)
    private String cfop;

    @Column(name = "icms_amount", nullable = false)
    private BigDecimal icmsAmount;
}
