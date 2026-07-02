package com.novafiscal.backend.purchase.api.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
public class PurchaseItemResponseDTO {

    private String description;

    private Integer quantity;

    private BigDecimal price;
}
