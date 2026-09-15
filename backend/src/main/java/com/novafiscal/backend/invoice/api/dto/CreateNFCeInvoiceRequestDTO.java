package com.novafiscal.backend.invoice.api.dto;

import com.novafiscal.backend.invoice.domain.model.PaymentMethod;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.util.UUID;

public record CreateNFCeInvoiceRequestDTO(
        @Schema(description = "ID do cliente")
        @NotNull
        UUID customerId,

        @Schema(description = "ID da compra vinculada (opcional)")
        UUID purchaseId,

        @Schema(description = "Valor total do cupom", example = "89.90")
        @NotNull
        @Positive
        BigDecimal totalAmount,

        @Schema(description = "CPF do consumidor (opcional)")
        String consumerCpf,

        @Schema(description = "Forma de pagamento", example = "CREDIT_CARD")
        @NotNull
        PaymentMethod paymentMethod,

        @Schema(description = "Valor do troco (se pagamento em dinheiro)")
        BigDecimal changeAmount
) {}