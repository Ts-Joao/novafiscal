package com.novafiscal.backend.purchase.api.dto;

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

    private UUID id;

    private String customerName;

    private BigDecimal totalAmount;

    private Instant createdAt;

    private List<PurchaseItemResponseDTO> items;
}
