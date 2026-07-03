package com.novafiscal.backend.purchase.domain.model;

import com.novafiscal.backend.common.exception.DomainException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class PurchaseItemTest {

    PurchaseItem itemA;

    @BeforeEach
    void setUp() {
        itemA = PurchaseItem.builder()
                .price(BigDecimal.valueOf(20))
                .quantity(2)
                .build();
    }

    @Nested
    class CalculateSubTotal {

        @Test
        void shouldCalculateSubTotal_whenCalled() {
            BigDecimal result = itemA.calculateSubtotal();
            assertEquals(BigDecimal.valueOf(40), result);
        }
    }

    @Nested
    class Validate {

        @Test
        void shouldValidateSuccessfully_whenCalled() {
            assertDoesNotThrow(() -> itemA.validate());
        }

        @Test
        void shouldThrowException_whenPriceIsNull() {
            PurchaseItem itemB = itemA.toBuilder()
                    .price(null)
                    .build();

            assertThrows(DomainException.class, itemB::validate);
        }

        @Test
        void shouldThrowException_whenPriceIsZeroOrNegative() {
            PurchaseItem itemB = itemA.toBuilder()
                .price(BigDecimal.valueOf(-1))
                .build();

            assertThrows(DomainException.class, itemB::validate);
        }

        @Test
        void shouldThrowException_whenQuantityIsNull() {
            PurchaseItem itemB = itemA.toBuilder()
                    .quantity(null)
                    .build();

            assertThrows(DomainException.class, itemB::validate);
        }

        @Test
        void shouldThrowException_whenQuantityIsZeroOrNegative() {
            PurchaseItem itemB = itemA.toBuilder()
                    .quantity(-1)
                    .build();

            assertThrows(DomainException.class, itemB::validate);
        }
    }
}
