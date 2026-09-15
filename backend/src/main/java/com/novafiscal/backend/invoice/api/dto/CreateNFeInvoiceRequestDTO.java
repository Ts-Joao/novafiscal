package com.novafiscal.backend.invoice.api.dto;

import com.novafiscal.backend.common.domain.model.DocumentType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.util.UUID;

public record CreateNFeInvoiceRequestDTO(
        @Schema(description = "ID do cliente", example = "509413f5-7964-4c2b-98d0-6fedf57ca8f3")
        @NotNull
        UUID customerId,

        @Schema(description = "ID da compra vinculada (opcional)")
        UUID purchaseId,

        @Schema(description = "Valor total da nota", example = "1500.00")
        @NotNull
        @Positive
        BigDecimal totalAmount,

        @Schema(description = "Número do documento do cliente", example = "12345678000199")
        @NotBlank
        String customerDocumentNumber,

        @Schema(description = "Tipo do documento", example = "CNPJ")
        @NotNull
        DocumentType customerDocumentType,

        @Schema(description = "Inscrição Estadual do cliente", example = "123456789")
        @NotBlank
        String customerStateRegistration,

        @Schema(description = "Natureza da operação", example = "Venda de mercadoria")
        @NotBlank
        String operationNature,

        @Schema(description = "CFOP", example = "5102")
        @NotBlank
        String cfop,

        @Schema(description = "Valor de ICMS", example = "270.00")
        @NotNull
        BigDecimal icmsAmount
) {}