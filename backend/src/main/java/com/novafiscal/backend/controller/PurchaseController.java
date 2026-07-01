package com.novafiscal.backend.controller;

import com.novafiscal.backend.dto.PurchaseRequestDTO;
import com.novafiscal.backend.service.PurchaseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/apí/v1/purchases")
public class PurchaseController {

    private final PurchaseService purchaseService;

    @PostMapping
    public ResponseEntity<String> createPurchase(
            @Valid @RequestBody PurchaseRequestDTO dto
            ) {
        String id = purchaseService.createPurchase(dto);
        return ResponseEntity.ok(id);
    }
}
