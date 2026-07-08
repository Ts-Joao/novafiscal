package com.novafiscal.backend.customer.api.dto;

import com.novafiscal.backend.customer.domain.model.CustomerStatus;
import com.novafiscal.backend.customer.domain.model.CustomerType;
import com.novafiscal.backend.customer.domain.model.DocumentType;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public record CustomerResponseDTO(

        UUID id,
        CustomerType customerType,
        DocumentType documentType,
        String legalName,
        String phone,
        String email,
        String stateRegistration,
        CustomerStatus status,
        List<AddressResponseDTO> addresses,
        Instant createdAt,
        Instant updatedAt
) {}
