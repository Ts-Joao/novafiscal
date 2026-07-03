package com.novafiscal.backend.purchase.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
public class PurchaseItemRequestDTO {
    
    @Schema(description = "Id do item", example = "8e6bd111-89e2-4615-bcfe-083b81a9d7e6")
    private UUID id;

    @Schema(description = "Descrição do item", example = "Produto 1")
    @NotBlank(message = "Descrição é obrigatória")
    private String description;

    @Schema(description = "Quantidade", example = "1")
    @NotNull(message = "Quantidade é obrigatória")
    @Min(value = 1, message = "Quantidade deve ser maior que zero")
    private Integer quantity;

    @Schema(description = "Preço", example = "10.00")
    @NotNull(message = "Preço é obrigatório")
    @DecimalMin(value = "0.01", message = "Preço deve ser maior que zero")
    private BigDecimal price;
}
