package com.novafiscal.backend.purchase.domain.model;

import com.novafiscal.backend.common.exception.DomainException;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class PurchaseItem {

    private UUID id;
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
            throw new DomainException("Price cannot be negative or zero for item: " + description);
        }
    }

    private void validateQuantity() {
        if (quantity == null || quantity < 0) {
            throw new DomainException("Quantity must be greater than 0 for item: " + description);
        }
    }

    public void validate() {
        validatePrice();
        validateQuantity();
    }
}
