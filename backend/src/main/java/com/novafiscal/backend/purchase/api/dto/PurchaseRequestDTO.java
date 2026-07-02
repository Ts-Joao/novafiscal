package com.novafiscal.backend.purchase.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PurchaseRequestDTO {

    @NotBlank
    private String customerName;

    @NotNull
    private List<PurchaseItemRequestDTO> items;
}
