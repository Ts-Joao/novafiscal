package com.novafiscal.backend.purchase.domain.repository;

import com.novafiscal.backend.purchase.domain.model.Purchase;
import com.novafiscal.backend.purchase.infrastructure.persistence.PurchaseJpaEntity;
import com.novafiscal.backend.purchase.infrastructure.persistence.SpringDataPurchaseRepository;
import com.novafiscal.backend.purchase.mapper.PurchaseEntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PurchaseRepositoryImpl implements PurchaseRepository {

    private final SpringDataPurchaseRepository springDataPurchaseRepository;
    private final PurchaseEntityMapper purchaseEntityMapper;

    @Override
    public Purchase save(Purchase purchase) {
        PurchaseJpaEntity entity = purchaseEntityMapper.toEntity(purchase);

        entity.getItems().forEach(item -> item.setPurchase(entity));

        PurchaseJpaEntity saved = springDataPurchaseRepository.save(entity);
        return purchaseEntityMapper.toDomain(saved);
    }
}
