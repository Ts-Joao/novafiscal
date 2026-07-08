package com.novafiscal.backend.customer.api.dto;

import com.novafiscal.backend.customer.domain.model.AddressType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AddAddressRequestDTO(

    @NotNull
    AddressType type,

    @NotBlank
    String street,

    String number,

    String complement,

    @NotBlank
    String neighborhood,

    @NotBlank
    String city,

    @NotBlank
    String state,

    @NotBlank
    String zipCode,

    boolean isDefault
) {}
