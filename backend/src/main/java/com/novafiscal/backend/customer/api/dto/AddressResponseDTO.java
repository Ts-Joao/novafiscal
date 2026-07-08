package com.novafiscal.backend.customer.api.dto;

import com.novafiscal.backend.customer.domain.model.AddressType;

import java.util.UUID;

public record AddressResponseDTO(
    UUID id,
    AddressType type,
    String street,
    String number,
    String complement,
    String neighborhood,
    String city,
    String state,
    String zipCode,
    boolean isDefault
) {}
