package com.novafiscal.backend.invoice.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SpringDataInvoiceJpaEntity extends JpaRepository<InvoiceJpaEntity, UUID> {

    List<InvoiceJpaEntity> findByCustomerId(UUID customerId);

    Optional<InvoiceJpaEntity> findByAccessKey(String accessKey);
}
