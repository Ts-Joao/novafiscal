package com.novafiscal.backend.purchase.infrastructure.persistence;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "purchase_item")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PurchaseItemJpaEntity {

    @Id
    private UUID id;

    @Setter
    @ManyToOne
    @JoinColumn(name = "purchase_id", nullable = false)
    private PurchaseJpaEntity purchase;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "price", nullable = false)
    private BigDecimal price;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;
}
