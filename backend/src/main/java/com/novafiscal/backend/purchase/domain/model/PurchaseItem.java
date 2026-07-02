package com.novafiscal.backend.purchase.domain.model;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class PurchaseItem {

    private String description;
    private BigDecimal price;
    private Integer quantity;
}
