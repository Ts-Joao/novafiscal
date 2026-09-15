package com.novafiscal.backend.invoice.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.UUID;

public record CreateInvoiceRequestDTO(
        @Schema(description = "ID do cliente", example = "123e4567-e89b-12d3-a456-426614174000")
        @NotNull
        UUID customerId,

        @Schema(description = "ID da compra", example = "123e4567-e89b-12d3-a456-426614174000")
        @NotNull
        UUID purchaseId,

        @Schema(description = "Valor total da nota", example = "123.45")
        @NotNull
        BigDecimal totalAmount,

        @Schema(description = "Inscrição estadual do cliente", example = "123456789")
        @NotNull
        String customerStateRegistration,

        @Schema(description = "Número da operação", example = "123456")
        @NotNull
        Integer operationNumber,

        @Schema(description = "CFOP da operação", example = "5101")
        @NotNull
        Integer cfop,

        @Schema(description = "Valor do ICMS", example = "12.34")
        @NotNull
        BigDecimal icmsAmount
) {
}
