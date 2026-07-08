package com.novafiscal.backend.customer.api.dto;

import com.novafiscal.backend.customer.domain.model.AddressType;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AddAddressRequestDTO(

    @Schema(description = "Tipo de endereço", example = "MAIN")
    @NotNull
    AddressType type,

    @Schema(description = "Rua", example = "Rua das Flores")
    @NotBlank
    String street,

    @Schema(description = "Número", example = "123")
    String number,

    @Schema(description = "Complemento", example = "Apto 101")
    String complement,

    @Schema(description = "Bairro", example = "Centro")
    @NotBlank
    String neighborhood,

    @Schema(description = "Cidade", example = "São Paulo")
    @NotBlank
    String city,

    @Schema(description = "Estado", example = "SP")
    @NotBlank
    String state,

    @Schema(description = "CEP", example = "12345678")
    @NotBlank
    String zipCode,

    @Schema(description = "Endereço padrão", example = "true")
    boolean isDefault
) {}
