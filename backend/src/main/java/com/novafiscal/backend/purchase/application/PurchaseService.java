package com.novafiscal.backend.purchase.application;

import com.novafiscal.backend.purchase.domain.model.Purchase;
import org.springframework.stereotype.Service;

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
