package com.novafiscal.backend.purchase.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
public class PurchaseItemResponseDTO {

    @NotBlank
    private String description;

    @NotNull
    private Integer quantity;

    @NotNull
    private Integer price;
}
