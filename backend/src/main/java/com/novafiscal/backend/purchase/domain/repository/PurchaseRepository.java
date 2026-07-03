package com.novafiscal.backend.purchase.domain.repository;

import com.novafiscal.backend.purchase.domain.model.Purchase;

public interface PurchaseRepository {

    Purchase save(Purchase purchase);
}
