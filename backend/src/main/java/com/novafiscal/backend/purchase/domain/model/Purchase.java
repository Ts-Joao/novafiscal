package com.novafiscal.backend.purchase.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Purchase {

    private UUID id;
    private String customerName;
    private Instant createdAt;
    private BigDecimal totalAmount;

    private List<PurchaseItem> items;

    private BigDecimal calculateTotalAmount() {
        if (items == null || items.isEmpty()) {
            return BigDecimal.ZERO;
        }

        return items.stream()
            .map(PurchaseItem::calculateSubtotal)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public void generateIdentifier() {
        this.id = UUID.randomUUID();
    }

    public void markAsCreated() {
        this.createdAt = Instant.now();
    }

    public void updateTotalAmount() {
        this.totalAmount = calculateTotalAmount();
    }

    private boolean hasItems() {
        return items != null && !items.isEmpty();
    }

    public void validate() {
        if (!hasItems()) {
            throw new IllegalArgumentException("Purchase must contain at least one item");
        }

        items.forEach(PurchaseItem::validate);
    }
}
