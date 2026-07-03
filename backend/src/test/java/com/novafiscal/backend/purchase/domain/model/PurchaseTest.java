package com.novafiscal.backend.purchase.domain.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PurchaseTest {

    Purchase purchase;

    @BeforeEach
    void setUp() {
        purchase = Purchase.builder()
                .customerName("João Teixeira")
                .items(List.of(
                        PurchaseItem.builder().price(BigDecimal.valueOf(20)).quantity(2).build(),
                        PurchaseItem.builder().price(BigDecimal.valueOf(5)).quantity(3).build()
                ))
                .build();
    }

    @Nested
    class UpdateTotalAmount {

        @Test
        void shouldSumAllItemSubTotal_whenCalled() {
            purchase.updateTotalAmount();
            assertEquals(BigDecimal.valueOf(55), purchase.getTotalAmount());
        }

        @Test
        void shouldResultInZero_whenItemsInListIsEmpty() {
            purchase = purchase.toBuilder().items(List.of()).build();
            purchase.updateTotalAmount();
            assertEquals(BigDecimal.ZERO, purchase.getTotalAmount());
        }
    }

    @Nested
    class Validate {

        @Test
        void shouldValidateSuccessfully_whenItemsExist() {
            assertDoesNotThrow(() -> purchase.validate());
        }

        @Test
        void shouldThrowException_whenItemsIsNull() {
            purchase = purchase.toBuilder().items(null).build();
            assertThrows(IllegalArgumentException.class, purchase::validate);
        }

        @Test
        void shouldThrowException_whenListIsEmpty() {
            purchase = purchase.toBuilder().items(List.of()).build();
            assertThrows(IllegalArgumentException.class, purchase::validate);
        }
    }

    @Test
    void shouldGenerateNonNullIdentifier_whenCalled() {
        purchase.generateIdentifier();
        assertNotNull(purchase.getId());
    }

    @Test
    void shouldSetCreatedAtToNonNullValue_whenMarkedAsCreated() {
        purchase.markAsCreated();
        assertNotNull(purchase.getCreatedAt());
    }
}
