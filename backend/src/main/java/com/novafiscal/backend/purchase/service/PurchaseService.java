package com.novafiscal.backend.purchase.service;

import com.novafiscal.backend.purchase.dto.PurchaseRequestDTO;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class PurchaseService {

    public String createPurchase(PurchaseRequestDTO dto) {
        if (dto.getItems().isEmpty()) {
            throw new IllegalArgumentException("Purchase must contain at least one item");
        }

        return UUID.randomUUID().toString();
    }
}
