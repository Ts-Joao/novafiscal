package com.novafiscal.backend.purchase.domain.service;

import com.novafiscal.backend.purchase.api.dto.PurchaseItemResponseDTO;
import com.novafiscal.backend.purchase.api.dto.PurchaseItemRequestDTO;
import com.novafiscal.backend.purchase.api.dto.PurchaseRequestDTO;
import com.novafiscal.backend.purchase.api.dto.PurchaseResponseDTO;
import com.novafiscal.backend.purchase.domain.model.Purchase;
import com.novafiscal.backend.purchase.domain.model.PurchaseItem;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
public class PurchaseService {

    public Purchase create(Purchase purchase) {

        purchase.validate();

        purchase.generateIdentifier();

        purchase.markAsCreated();

        purchase.updateTotalAmount();

        return purchase;
    }
}
