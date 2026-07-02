package com.novafiscal.backend.purchase.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class PurchaseRequestDTO {

    @NotBlank
    private String customerName;

    @NotNull
    private BigDecimal totalAmount;

    @NotNull
    private List<PurchaseItemRequestDTO> items;
}
