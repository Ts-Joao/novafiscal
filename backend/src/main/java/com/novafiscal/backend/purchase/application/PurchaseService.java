package com.novafiscal.backend.purchase.application;

import com.novafiscal.backend.common.exception.ResourceNotFoundException;
import com.novafiscal.backend.purchase.domain.model.Purchase;
import com.novafiscal.backend.purchase.domain.repository.PurchaseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PurchaseService {

    private final PurchaseRepository purchaseRepository;

    public Purchase create(Purchase purchase) {
        purchase.validate();
        purchase.generateIdentifier();
        purchase.markAsCreated();
        purchase.updateTotalAmount();
        return purchaseRepository.save(purchase);
    }

    public Purchase findById(UUID id) {
        return purchaseRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Compra não encontrada: " + id));
    }
}
