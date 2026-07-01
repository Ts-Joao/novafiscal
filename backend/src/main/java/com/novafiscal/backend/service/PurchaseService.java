package com.novafiscal.backend.service;

import com.novafiscal.backend.dto.PurchaseRequestDTO;
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
