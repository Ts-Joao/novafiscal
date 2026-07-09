package com.novafiscal.backend.customer.domain.repository;

import com.novafiscal.backend.customer.domain.model.Customer;

import java.util.Optional;
import java.util.UUID;

public interface CustomerRepository {

    Customer save(Customer customer);

    Optional<Customer> findById(UUID id);

    Optional<Customer> findByDocumentNumber(String documentNumber);

    boolean existsByDocumentNumber(String documentNumber);

    void deleteAll();
}
