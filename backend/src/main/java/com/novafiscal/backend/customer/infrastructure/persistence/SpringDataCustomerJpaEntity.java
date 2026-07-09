package com.novafiscal.backend.customer.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface SpringDataCustomerJpaEntity extends JpaRepository<CustomerJpaEntity, UUID> {

    Optional<CustomerJpaEntity> findByDocumentNumber(String documentNumber);

    boolean existsByDocumentNumber(String documentNumber);
}
