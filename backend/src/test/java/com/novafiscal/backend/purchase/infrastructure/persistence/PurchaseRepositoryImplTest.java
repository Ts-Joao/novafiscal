package com.novafiscal.backend.purchase.infrastructure.persistence;

import com.novafiscal.backend.common.AbstractIntegrationTest;
import com.novafiscal.backend.purchase.domain.model.Purchase;
import com.novafiscal.backend.purchase.domain.model.PurchaseItem;
import com.novafiscal.backend.purchase.domain.repository.PurchaseRepositoryImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@Testcontainers
@SpringBootTest
class PurchaseRepositoryImplTest extends AbstractIntegrationTest {

    @Autowired
    PurchaseRepositoryImpl purchaseRepositoryImpl;

    PurchaseItem itemA;
    Purchase purchaseA;

    @BeforeEach
    void setUp() {
        itemA = PurchaseItem.builder()
                .id(UUID.randomUUID())
                .description("Item A")
                .price(BigDecimal.valueOf(20))
                .quantity(2)
                .build();

        purchaseA = Purchase.builder()
                .id(UUID.randomUUID())
                .customerName("João Teixeira")
                .items(List.of(itemA))
                .build();
        purchaseA.markAsCreated();
        purchaseA.updateTotalAmount();
    }

    @Nested
    class Save {
        @Test
        void shouldPersistAndRetrievePurchaseWithItems() {
            Purchase saved = purchaseRepositoryImpl.save(purchaseA);

            assertThat(saved.getId()).isEqualTo(purchaseA.getId());
            assertThat(saved.getTotalAmount()).isEqualByComparingTo(BigDecimal.valueOf(40));
            assertThat(saved.getItems()).hasSize(1);
        }
    }

    @Nested
    class FindById {
        @Test
        void shouldReturnEmpty_whenPurchaseDoesNotExist() {
            Optional<Purchase> result = purchaseRepositoryImpl.findById(UUID.randomUUID());
            assertThat(result).isEmpty();
        }

        @Test
        void shouldReturnPurchase_whenExist() {
            Purchase saved = purchaseRepositoryImpl.save(purchaseA);

            Optional<Purchase> result = purchaseRepositoryImpl.findById(saved.getId());

            assertThat(result).isPresent();
            assertThat(result.get().getCustomerName()).isEqualTo(saved.getCustomerName());
        }
    }
}
