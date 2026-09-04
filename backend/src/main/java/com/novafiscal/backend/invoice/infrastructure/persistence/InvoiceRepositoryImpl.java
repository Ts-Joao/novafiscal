package com.novafiscal.backend.invoice.infrastructure.persistence;

import com.novafiscal.backend.invoice.domain.model.Invoice;
import com.novafiscal.backend.invoice.domain.repository.InvoiceRepository;
import com.novafiscal.backend.invoice.mapper.InvoiceEntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class InvoiceRepositoryImpl implements InvoiceRepository {

    private final SpringDataInvoiceJpaEntity springDataInvoiceJpaEntity;
    private final InvoiceEntityMapper invoiceEntityMapper;

    @Override
    public Invoice save(Invoice invoice) {
        InvoiceJpaEntity entity = invoiceEntityMapper.toEntity(invoice);
        InvoiceJpaEntity saved = springDataInvoiceJpaEntity.save(entity);
        return invoiceEntityMapper.toDomain(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Invoice> findById(UUID id) {
        return springDataInvoiceJpaEntity.findById(id)
                .map(invoiceEntityMapper::toDomain);
    }

    @Override
    public List<Invoice> findByCustomerId(UUID customerId) {
        return springDataInvoiceJpaEntity.findByCustomerId(customerId)
                .stream().map(invoiceEntityMapper::toDomain).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Invoice> findByAccessKey(String accessKey) {
        return springDataInvoiceJpaEntity.findByAccessKey(accessKey)
                .map(invoiceEntityMapper::toDomain);
    }
}
