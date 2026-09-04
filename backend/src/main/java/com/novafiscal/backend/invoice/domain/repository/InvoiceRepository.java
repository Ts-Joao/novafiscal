package com.novafiscal.backend.invoice.domain.repository;

import com.novafiscal.backend.invoice.domain.model.Invoice;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface InvoiceRepository {

    Invoice save(Invoice invoice);

    Optional<Invoice> findById(UUID id);

    List<Invoice> findByCustomerId(UUID customerId);

    Optional<Invoice> findByAccessKey(String accessKey);
}
