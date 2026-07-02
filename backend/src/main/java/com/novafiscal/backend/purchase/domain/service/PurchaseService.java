package com.novafiscal.backend.purchase.domain.service;

import com.novafiscal.backend.purchase.api.dto.PurchaseItemResponseDTO;
import com.novafiscal.backend.purchase.api.dto.PurchaseItemRequestDTO;
import com.novafiscal.backend.purchase.api.dto.PurchaseRequestDTO;
import com.novafiscal.backend.purchase.api.dto.PurchaseResponseDTO;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
public class PurchaseService {

    public PurchaseResponseDTO createPurchase(PurchaseRequestDTO dto) {
        validatePurchaseItems(dto);
        validatePrice(dto);
        validateQuantity(dto);
        BigDecimal total = calculateTotalAmount(dto);
        UUID purchaseId = generatePurchaseId();

        return PurchaseResponseDTO.builder()
                .id(purchaseId)
                .customerName(dto.getCustomerName())
                .totalAmount(total)
                .createdAt(Instant.now())
                .items(mapRequestItemToResponse(dto.getItems()))
                .build();
    }

    private void validatePurchaseItems(PurchaseRequestDTO dto) {
        if (dto.getItems() == null || dto.getItems().isEmpty()) {
            throw new IllegalArgumentException("Purchase must contain at least one item");
        }
    }

    private BigDecimal calculateTotalAmount(PurchaseRequestDTO dto) {
        return dto.getItems().stream()
                .map(item -> BigDecimal.valueOf(item.getPrice()).multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private UUID generatePurchaseId() {
        return UUID.randomUUID();
    }

    private void validatePrice(PurchaseRequestDTO dto) {
        for (PurchaseItemRequestDTO item: dto.getItems()) {
            if (item.getPrice() < 0) {
                throw new IllegalArgumentException("Price cannot be negative for item: " + item.getDescription());
            }
        }
    }

    private void validateQuantity(PurchaseRequestDTO dto) {
        for (PurchaseItemRequestDTO item: dto.getItems()) {
            if (item.getQuantity() < 0) {
                throw new IllegalArgumentException("Quantity must be greater than 0 for item: " + item.getDescription());
            }
        }
    }

    private List<PurchaseItemResponseDTO> mapRequestItemToResponse(List<PurchaseItemRequestDTO> requestItems) {
        return requestItems.stream()
                .map(item -> PurchaseItemResponseDTO.builder()
                    .description(item.getDescription())
                    .quantity(item.getQuantity())
                    .price(item.getPrice())
                    .build())
            .toList();
    }
}
