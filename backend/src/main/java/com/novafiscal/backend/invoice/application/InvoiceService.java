package com.novafiscal.backend.invoice.application;

import com.novafiscal.backend.common.exception.ResourceNotFoundException;
import com.novafiscal.backend.invoice.domain.model.Invoice;
import com.novafiscal.backend.invoice.domain.repository.InvoiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class InvoiceService {

    private final InvoiceRepository invoiceRepository;

    public Invoice create(Invoice invoice) {
        return invoiceRepository.save(invoice);
    }

    public Invoice findById(UUID id) {
        return invoiceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Invoice not found!"));
    }

    public List<Invoice> findByCustomerId(UUID customerId) {
        return invoiceRepository.findByCustomerId(customerId);
    }

    public Invoice findByAccessKey(String accessKey) {
        return invoiceRepository.findByAccessKey(accessKey)
                .orElseThrow(() -> new ResourceNotFoundException("Invoice with accessKey " + accessKey + " not found!"));
    }
}
