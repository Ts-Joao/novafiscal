package com.novafiscal.backend.customer.infraestructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SpringDataCustomerJpaEntity extends JpaRepository<CustomerJpaEntity, UUID> {
}
