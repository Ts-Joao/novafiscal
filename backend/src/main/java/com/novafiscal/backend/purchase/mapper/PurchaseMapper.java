package com.novafiscal.backend.purchase.mapper;

import com.novafiscal.backend.purchase.api.dto.PurchaseItemRequestDTO;
import com.novafiscal.backend.purchase.api.dto.PurchaseItemResponseDTO;
import com.novafiscal.backend.purchase.api.dto.PurchaseRequestDTO;
import com.novafiscal.backend.purchase.api.dto.PurchaseResponseDTO;
import com.novafiscal.backend.purchase.domain.model.Purchase;
import com.novafiscal.backend.purchase.domain.model.PurchaseItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface PurchaseMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "totalAmount", ignore = true)
    Purchase toDomain(PurchaseRequestDTO dto);

    PurchaseResponseDTO toResponse(Purchase purchase);

    @Mapping(target = "id", ignore = true)
    PurchaseItem toDomain(PurchaseItemRequestDTO dto);

    PurchaseItemResponseDTO toResponse(PurchaseItem item);
}
