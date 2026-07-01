package com.novafiscal.backend.dto;

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
    private List<ItemDTO> items;

    @Getter
    @Setter
    public static class ItemDTO {

        @NotBlank
        private String description;

        @NotNull
        private Integer quantity;

        @NotNull
        private Integer price;
    }
}
