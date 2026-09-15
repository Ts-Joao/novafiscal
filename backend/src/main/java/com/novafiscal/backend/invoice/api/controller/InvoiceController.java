package com.novafiscal.backend.invoice.api.controller;

import com.novafiscal.backend.common.config.swagger.ApiDocException;
import com.novafiscal.backend.invoice.api.dto.CreateNFCeInvoiceRequestDTO;
import com.novafiscal.backend.invoice.api.dto.CreateNFeInvoiceRequestDTO;
import com.novafiscal.backend.invoice.api.dto.InvoiceResponseDTO;
import com.novafiscal.backend.invoice.application.InvoiceService;
import com.novafiscal.backend.invoice.domain.model.Invoice;
import com.novafiscal.backend.invoice.mapper.InvoiceMapper;
import com.novafiscal.backend.common.response.ApiResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Tag(name = "Invoice", description = "Operações relacionadas à emissão de documentos fiscais")
@RequiredArgsConstructor
@RestController
@RequestMapping("/invoices")
@ApiDocException
public class InvoiceController {

    private final InvoiceService invoiceService;
    private final InvoiceMapper invoiceMapper;

    @Operation(summary = "Emite uma NF-e", description = "Cria uma nova nota fiscal eletrônica (B2B)")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "201",
            description = "Invoice created successfully"
        ),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "400",
            description = "Invalid request"
        )
    })
    @PostMapping("/nfe")
    public ResponseEntity<ApiResponse<InvoiceResponseDTO>> createNFeInvoice(
            @RequestBody @Valid CreateNFeInvoiceRequestDTO dto
    ) {
        Invoice invoice = invoiceMapper.toDomain(dto);
        Invoice created = invoiceService.create(invoice);
        InvoiceResponseDTO response = invoiceMapper.toResponse(created);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ApiResponse<>(Instant.now(), response));
    }

    @Operation(summary = "Emite uma NFC-e", description = "Cria um novo cupom fiscal eletrônico (varejo)")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "201",
            description = "NFCe invoice created successfully"
        ),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "400",
            description = "Invalid request"
        )
    })
    @PostMapping("/nfce")
    public ResponseEntity<ApiResponse<InvoiceResponseDTO>> createNFCeInvoice(
            @RequestBody @Valid CreateNFCeInvoiceRequestDTO dto
    ) {
        Invoice invoice = invoiceMapper.toDomain(dto);
        Invoice created = invoiceService.create(invoice);
        InvoiceResponseDTO response = invoiceMapper.toResponse(created);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ApiResponse<>(Instant.now(), response));
    }

    @Operation(summary = "Busca uma nota fiscal por ID", description = "Retorna os dados de uma nota fiscal pelo ID")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "200",
            description = "Invoice found successfully"
        ),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "404",
            description = "Invoice not found"
        )
    })
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<InvoiceResponseDTO>> findById(@PathVariable UUID id) {
        Invoice invoice = invoiceService.findById(id);
        InvoiceResponseDTO response = invoiceMapper.toResponse(invoice);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ApiResponse<>(Instant.now(), response));
    }

    @Operation(summary = "Busca uma nota fiscal por ID do cliente", description = "Retorna os dados de uma nota fiscal pelo ID do cliente")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "200",
            description = "Invoices found successfully"
        ),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "404",
            description = "Invoices not found"
        )
    })
    @GetMapping("/{customerId}/customer")
    public ResponseEntity<ApiResponse<List<InvoiceResponseDTO>>> findByCustomerId(@PathVariable UUID customerId) {
        List<Invoice> invoices = invoiceService.findByCustomerId(customerId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ApiResponse<>(
                        Instant.now(),
                        invoices.stream().map(invoiceMapper::toResponse).toList()
                ));
    }

    @Operation(summary = "Busca uma nota fiscal por chave de acesso", description = "Retorna os dados de uma nota fiscal pela chave de acesso")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "200",
            description = "Invoice found successfully"
        ),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "404",
            description = "Invoice not found"
        )
    })
    @GetMapping("/{accessKey}/access-key")
    public ResponseEntity<ApiResponse<InvoiceResponseDTO>> findByAccessKey(@PathVariable String accessKey) {
        Invoice invoice =  invoiceService.findByAccessKey(accessKey);
        InvoiceResponseDTO response = invoiceMapper.toResponse(invoice);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ApiResponse<>(Instant.now(), response));
    }

    @Operation(summary = "Envia uma nota fiscal para autorização", description = "Envia uma nota fiscal para autorização")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "200",
            description = "Invoice submitted successfully"
        ),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "404",
            description = "Invoice not found"
        )
    })
    @PatchMapping("/{id}/submit")
    public ResponseEntity<ApiResponse<InvoiceResponseDTO>> submitInvoice(@PathVariable UUID id) {
        Invoice invoice = invoiceService.submit(id);
        InvoiceResponseDTO response = invoiceMapper.toResponse(invoice);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ApiResponse<>(Instant.now(), response));
    }

    @Operation(summary = "Autoriza uma nota fiscal", description = "Autoriza uma nota fiscal pelo ID")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "200",
            description = "Invoice authorized successfully"
        ),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "404",
            description = "Invoice not found"
        )
    })
    @PatchMapping("/{id}/authorize")
    public ResponseEntity<ApiResponse<InvoiceResponseDTO>> authorizeInvoice(
            @PathVariable UUID id,
            @RequestBody @Valid String protocolNumber,
            @RequestBody @Valid String accessKey
            ) {
        Invoice invoice = invoiceService.authorize(id, protocolNumber, accessKey);
        InvoiceResponseDTO response = invoiceMapper.toResponse(invoice);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ApiResponse<>(Instant.now(), response));
    }

    @Operation(summary = "Rejeita uma nota fiscal", description = "Rejeita uma nota fiscal pelo ID")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "200",
            description = "Invoice rejected successfully"
        ),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "404",
            description = "Invoice not found"
        )
    })
    @PatchMapping("/{id}/reject")
    public ResponseEntity<ApiResponse<InvoiceResponseDTO>> rejectInvoice(@PathVariable UUID id) {
        Invoice invoice = invoiceService.reject(id);
        InvoiceResponseDTO response = invoiceMapper.toResponse(invoice);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ApiResponse<>(Instant.now(), response));
    }

    @Operation(summary = "Cancela uma nota fiscal", description = "Cancela uma nota fiscal pelo ID")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "200",
            description = "Invoice canceled successfully"
        ),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "404",
            description = "Invoice not found"
        )
    })
    @PatchMapping("/{id}/cancel")
    public ResponseEntity<ApiResponse<InvoiceResponseDTO>> cancelInvoice(@PathVariable UUID id) {
        Invoice invoice = invoiceService.cancel(id);
        InvoiceResponseDTO response = invoiceMapper.toResponse(invoice);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ApiResponse<>(Instant.now(), response));
    }
}
