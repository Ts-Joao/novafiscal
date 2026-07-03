package com.novafiscal.backend.purchase.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
public class PurchaseResponseDTO {

    @Schema(description = "ID da compra", example = "a1b2c3d4-e5f6-7890-1234-567890abcdef")
    private UUID id;

    @Schema(description = "Nome do cliente", example = "Cliente 1")
    private String customerName;

    @Schema(description = "Valor total da compra", example = "100.00")
    private BigDecimal totalAmount;

    @Schema(description = "Data de criação da compra", example = "2022-01-01T00:00:00Z")
    private Instant createdAt;

    @Schema(description = "Itens da compra")
    private List<PurchaseItemResponseDTO> items;
}
