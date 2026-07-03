package com.novafiscal.backend.purchase.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PurchaseRequestDTO {

    @Schema(description = "Nome do cliente", example = "João Teixeira")
    @NotBlank
    private String customerName;

    @Schema(description = "Lista de itens da compra")
    @NotNull
    @Valid
    private List<PurchaseItemRequestDTO> items;
}
