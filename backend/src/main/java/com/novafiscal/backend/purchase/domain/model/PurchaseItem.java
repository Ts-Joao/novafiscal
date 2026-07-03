package com.novafiscal.backend.purchase.domain.model;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class PurchaseItem {

    private String description;
    private BigDecimal price;
    private Integer quantity;

    public BigDecimal calculateSubtotal() {
        if (price == null || quantity == null) {
            return BigDecimal.ZERO;
        }

        return price.multiply(BigDecimal.valueOf(quantity));
    }

    private void validatePrice() {
        if (price == null || price.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Price cannot be negative or zero for item: " + description);
        }
    }

    private void validateQuantity() {
        if (quantity == null || quantity < 0) {
            throw new IllegalArgumentException("Quantity must be greater than 0 for item: " + description);
        }
    }

    public void validate() {
        validatePrice();
        validateQuantity();
    }
}
