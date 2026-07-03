package com.novafiscal.backend.purchase.domain.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PurchaseTest {

    Purchase purchaseA;

    @BeforeEach
    void setUp() {
        purchaseA = Purchase.builder()
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
            purchaseA.updateTotalAmount();
            assertEquals(BigDecimal.valueOf(55), purchaseA.getTotalAmount());
        }

        @Test
        void shouldResultInZero_whenItemsInListIsEmpty() {
            Purchase purchaseB = purchaseA.toBuilder().items(List.of()).build();
            purchaseB.updateTotalAmount();
            assertEquals(BigDecimal.ZERO, purchaseB.getTotalAmount());
        }
    }

    @Nested
    class Validate {

        @Test
        void shouldValidateSuccessfully_whenItemsExist() {
            assertDoesNotThrow(() -> purchaseA.validate());
        }

        @Test
        void shouldThrowException_whenItemsIsNull() {
            Purchase purchaseB = purchaseA.toBuilder().items(null).build();
            assertThrows(IllegalArgumentException.class, purchaseB::validate);
        }

        @Test
        void shouldThrowException_whenListIsEmpty() {
            Purchase purchaseB = purchaseA.toBuilder().items(List.of()).build();
            assertThrows(IllegalArgumentException.class, purchaseB::validate);
        }
    }

    @Test
    void shouldGenerateNonNullIdentifier_whenCalled() {
        purchaseA.generateIdentifier();
        assertNotNull(purchaseA.getId());
    }

    @Test
    void shouldSetCreatedAtToNonNullValue_whenMarkedAsCreated() {
        purchaseA.markAsCreated();
        assertNotNull(purchaseA.getCreatedAt());
    }

    @Test
    void shouldThrowException_whenAnyItemIsInvalid() {
        Purchase purchaseB = purchaseA.toBuilder()
                .items(List.of(
                        PurchaseItem.builder().price(null).quantity(1).build()
                ))
                .build();
        assertThrows(IllegalArgumentException.class, purchaseB::validate);
    }
}
