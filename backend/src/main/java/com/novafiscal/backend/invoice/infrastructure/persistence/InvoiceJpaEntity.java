package com.novafiscal.backend.invoice.infrastructure.persistence;

import com.novafiscal.backend.invoice.domain.model.InvoiceStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Entity
@Getter
@Table(name = "invoice")
@SuperBuilder()
@Inheritance(strategy = InheritanceType.JOINED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DiscriminatorColumn(name = "invoice_type", discriminatorType = DiscriminatorType.STRING)
public abstract class InvoiceJpaEntity {

    @Id
    private UUID id;

    @Column(name  = "customer_id", nullable = false)
    private UUID customerId;

    @Column(name = "purchase_id")
    private UUID purchaseId;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private InvoiceStatus status;

    @Column(name = "access_key")
    private String accessKey;

    @Column(name = "protocol_number")
    private String protocolNumber;

    @Column(name = "total_amount", nullable = false)
    private BigDecimal totalAmount;

    @Column(name = "issued_at", nullable = false)
    private Instant issuedAt;

    @Column(name = "authorized_at")
    private Instant authorizedAt;

    @Column(name = "canceled_at")
    private Instant canceledAt;
}
