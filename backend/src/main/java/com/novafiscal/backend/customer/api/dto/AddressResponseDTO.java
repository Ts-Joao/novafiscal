package com.novafiscal.backend.customer.api.dto;

import com.novafiscal.backend.customer.domain.model.AddressType;

import java.util.UUID;

import io.swagger.v3.oas.annotations.media.Schema;

public record AddressResponseDTO(

    @Schema(description = "ID do endereço")
    UUID id,

    @Schema(description = "Tipo de endereço")
    AddressType type,

    @Schema(description = "Rua")
    String street,

    @Schema(description = "Número")
    String number,

    @Schema(description = "Complemento")
    String complement,

    @Schema(description = "Bairro")
    String neighborhood,

    @Schema(description = "Cidade")
    String city,

    @Schema(description = "Estado")
    String state,

    @Schema(description = "CEP")
    String zipCode,

    @Schema(description = "Endereço padrão")
    boolean isDefault
) {}
