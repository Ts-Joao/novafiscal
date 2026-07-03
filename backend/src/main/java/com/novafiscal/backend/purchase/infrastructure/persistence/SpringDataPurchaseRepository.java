package com.novafiscal.backend.purchase.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SpringDataPurchaseRepository extends JpaRepository<PurchaseJpaEntity, UUID> {
}
