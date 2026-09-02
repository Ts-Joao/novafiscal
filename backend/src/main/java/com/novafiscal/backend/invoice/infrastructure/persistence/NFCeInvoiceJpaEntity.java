package com.novafiscal.backend.invoice.infrastructure.persistence;

import com.novafiscal.backend.invoice.domain.model.PaymentMethod;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

@Entity
@Getter
@Table(name = "nfce_invoice")
@SuperBuilder()
@DiscriminatorValue("NFCE")
@PrimaryKeyJoinColumn(name = "id")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NFCeInvoiceJpaEntity extends InvoiceJpaEntity {

    @Column(name = "consumer_cpf")
    private String consumerCpf;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_method")
    private PaymentMethod paymentMethod;

    @Column(name = "change_amount")
    private BigDecimal changeAmount;
}
