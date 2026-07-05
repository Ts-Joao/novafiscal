package com.novafiscal.backend.customer.domain.repository;

import com.novafiscal.backend.customer.domain.model.Customer;
import com.novafiscal.backend.customer.infraestructure.persistence.CustomerJpaEntity;
import com.novafiscal.backend.customer.infraestructure.persistence.SpringDataCustomerJpaEntity;
import com.novafiscal.backend.customer.mapper.CustomerEntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class CustomerRepositoryImpl implements CustomerRepository {

    private final SpringDataCustomerJpaEntity springDataCustomerJpaEntity;
    private final CustomerEntityMapper customerEntityMapper;

    @Override
    public Customer save(Customer customer) {
        CustomerJpaEntity entity = customerEntityMapper.toEntity(customer);
        CustomerJpaEntity saved = springDataCustomerJpaEntity.save(entity);
        return customerEntityMapper.toDomain(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Customer> findById(UUID id) {
        return springDataCustomerJpaEntity.findById(id)
                .map(customerEntityMapper::toDomain);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Customer> findByDocumentNumber(String documentNumber) {
        return springDataCustomerJpaEntity.findByDocumentNumber(documentNumber)
                .map(customerEntityMapper::toDomain);
    }

    @Override
    public boolean existsByDocumentNumber(String documentNumber) {
        return springDataCustomerJpaEntity.existsByDocumentNumber(documentNumber);
    }
}
