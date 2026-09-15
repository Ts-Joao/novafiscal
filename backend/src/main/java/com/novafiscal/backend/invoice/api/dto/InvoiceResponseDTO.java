package com.novafiscal.backend.invoice.api.dto;

import com.novafiscal.backend.common.domain.model.DocumentType;
import com.novafiscal.backend.invoice.domain.model.InvoiceStatus;
import com.novafiscal.backend.invoice.domain.model.PaymentMethod;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public record InvoiceResponseDTO(
        UUID id,
        @Schema(description = "Tipo do documento fiscal", example = "NFE")
        String invoiceType,
        UUID customerId,
        UUID purchaseId,
        InvoiceStatus status,
        String accessKey,
        String protocolNumber,
        BigDecimal totalAmount,
        Instant issuedAt,
        Instant authorizedAt,
        Instant canceledAt,

        String customerDocumentNumber,
        DocumentType customerDocumentType,
        String customerStateRegistration,
        String operationNature,
        String cfop,
        BigDecimal icmsAmount,

        String consumerCpf,
        PaymentMethod paymentMethod,
        BigDecimal changeAmount
) {}