package com.novafiscal.backend.purchase.api.controller;

import com.novafiscal.backend.common.response.ApiResponse;
import com.novafiscal.backend.purchase.api.dto.PurchaseRequestDTO;
import com.novafiscal.backend.purchase.api.dto.PurchaseResponseDTO;
import com.novafiscal.backend.purchase.application.PurchaseService;
import com.novafiscal.backend.purchase.domain.model.Purchase;
import com.novafiscal.backend.purchase.mapper.PurchaseMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;

@RequiredArgsConstructor
@RestController
@RequestMapping("/purchases")
public class PurchaseController {

    private final PurchaseService purchaseService;
    private final PurchaseMapper purchaseMapper;

    @PostMapping
    public ResponseEntity<ApiResponse<PurchaseResponseDTO>> createPurchase(
            @Valid @RequestBody PurchaseRequestDTO dto) {

        Purchase purchase = purchaseMapper.toDomain(dto);
        Purchase created = purchaseService.create(purchase);
        PurchaseResponseDTO response = purchaseMapper.toResponse(created);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ApiResponse<>(Instant.now(), response));
    }
}
