package com.novafiscal.backend.customer.api.dto;

import com.novafiscal.backend.customer.domain.model.CustomerStatus;
import com.novafiscal.backend.customer.domain.model.CustomerType;
import com.novafiscal.backend.customer.domain.model.DocumentType;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public record CustomerResponseDTO(

        @Schema(description = "ID do cliente")
        UUID id,

        @Schema(description = "Tipo de cliente")
        CustomerType customerType,

        @Schema(description = "Documento do cliente")
        DocumentResponseDTO document,

        @Schema(description = "Nome legal")
        String legalName,

        @Schema(description = "Nome fantasia")
        String tradeName,

        @Schema(description = "Telefone")
        String phone,

        @Schema(description = "Email")
        String email,

        @Schema(description = "Inscrição estadual")
        String stateRegistration,

        @Schema(description = "Status")
        CustomerStatus status,

        @Schema(description = "Endereços")
        List<AddressResponseDTO> addresses,

        @Schema(description = "Data de criação")
        Instant createdAt,

        @Schema(description = "Data de atualização")
        Instant updatedAt
) {}
