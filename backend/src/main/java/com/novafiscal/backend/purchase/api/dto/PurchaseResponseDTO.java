package com.novafiscal.backend.purchase.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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

    @NotBlank
    private UUID id;

    @NotBlank
    private String customerName;

    @NotNull
    private BigDecimal totalAmount;

    @NotNull
    private Instant createdAt;

    @NotNull
    private List<PurchaseItemResponseDTO> items;
}
