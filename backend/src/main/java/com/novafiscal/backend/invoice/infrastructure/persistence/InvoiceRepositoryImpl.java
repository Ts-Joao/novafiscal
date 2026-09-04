package com.novafiscal.backend.invoice.infrastructure.persistence;

import com.novafiscal.backend.invoice.domain.model.Invoice;
import com.novafiscal.backend.invoice.domain.repository.InvoiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class InvoiceRepositoryImpl implements InvoiceRepository {

    private final SpringDataInvoiceJpaEntity springDataInvoiceJpaEntity;

    @Override
    public Invoice save(Invoice invoice) {
        return null;
    }

    @Override
    public Optional<Invoice> findById(UUID id) {
        return Optional.empty();
    }

    @Override
    public List<Invoice> findByCustomerId(UUID customerId) {
        return null;
    }

    @Override
    public Optional<Invoice> findByAccessKey(String accessKey) {
        return Optional.empty();
    }
}
