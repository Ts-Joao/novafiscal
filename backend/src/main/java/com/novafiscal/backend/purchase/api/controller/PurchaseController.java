package com.novafiscal.backend.purchase.api.controller;

import com.novafiscal.backend.common.config.swagger.ApiDocException;
import com.novafiscal.backend.common.response.ApiResponse;
import com.novafiscal.backend.purchase.api.dto.PurchaseRequestDTO;
import com.novafiscal.backend.purchase.api.dto.PurchaseResponseDTO;
import com.novafiscal.backend.purchase.application.PurchaseService;
import com.novafiscal.backend.purchase.domain.model.Purchase;
import com.novafiscal.backend.purchase.mapper.PurchaseMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.UUID;

@Tag(name = "Purchase", description = "Operações relacionadas ao processamento de compras")
@RequiredArgsConstructor
@RestController
@RequestMapping("/purchases")
@ApiDocException
public class PurchaseController {

    private final PurchaseService purchaseService;
    private final PurchaseMapper purchaseMapper;

    @Operation(
            summary = "Cria uma nova compra",
            description = "Recebe os dados de uma compra, valida as regras de negócio do domínio "
                    + "(itens obrigatórios, preço e quantidade válidos) e retorna a compra criada, "
                    + "já com identificador, data de criação e valor total calculados."
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "201",
                    description = "Compra criada com sucesso"
            )
    })
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

    @Operation(
        summary = "Busca uma compra pelo seu identificador único",
        description = "Retorna os detalhes completos de uma compra, incluindo itens, data e valor total."
    )
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "200",
            description = "Compra encontrada com sucesso"
        )
    })
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<PurchaseResponseDTO>> findPurchaseById(@PathVariable UUID id) {
        Purchase purchase = purchaseService.findById(id);
        PurchaseResponseDTO response = purchaseMapper.toResponse(purchase);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ApiResponse<>(Instant.now(), response));
    }
}
