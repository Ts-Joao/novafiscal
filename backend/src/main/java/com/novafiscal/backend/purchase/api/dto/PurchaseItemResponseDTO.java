package com.novafiscal.backend.purchase.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
public class PurchaseItemResponseDTO {

    @Schema(description = "Descrição do item", example = "Produto 1")
    private String description;

    @Schema(description = "Quantidade", example = "1")
    private Integer quantity;

    @Schema(description = "Preço", example = "10.00")
    private BigDecimal price;
}
