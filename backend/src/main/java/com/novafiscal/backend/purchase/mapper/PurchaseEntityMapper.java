package com.novafiscal.backend.purchase.mapper;

import com.novafiscal.backend.purchase.domain.model.Purchase;
import com.novafiscal.backend.purchase.domain.model.PurchaseItem;
import com.novafiscal.backend.purchase.infrastructure.persistence.PurchaseItemJpaEntity;
import com.novafiscal.backend.purchase.infrastructure.persistence.PurchaseJpaEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface PurchaseEntityMapper {

    @Mapping(target = "items", source = "items")
    PurchaseJpaEntity toEntity(Purchase purchase);

    @Mapping(target = "purchase", ignore = true)
    PurchaseItemJpaEntity toEntity(PurchaseItem item);

    Purchase toDomain(PurchaseJpaEntity entity);

    PurchaseItem toDomain(PurchaseItemJpaEntity entity);
}
