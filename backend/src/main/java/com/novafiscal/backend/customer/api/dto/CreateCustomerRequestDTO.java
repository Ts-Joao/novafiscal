package com.novafiscal.backend.customer.api.dto;

import com.novafiscal.backend.customer.domain.model.CustomerType;
import com.novafiscal.backend.customer.domain.model.DocumentType;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateCustomerRequestDTO(

    @Schema(description = "Tipo de cliente", example = "INDIVIDUAL")
    @NotNull
    CustomerType customerType,

    @Schema(description = "Número do documento", example = "12345678909")
    @NotBlank
    String documentNumber,

    @Schema(description = "Tipo do documento", example = "CPF")
    @NotNull
    DocumentType documentType,

    @Schema(description = "Nome legal", example = "João Teixeira")
    String legalName,

    @Schema(description = "Nome fantasia", example = "Novafiscal")
    String tradeName,

    @Schema(description = "Telefone", example = "11999999999")
    String phone,

    @Schema(description = "Email", example = "joao.teixeira@example.com")
    @Email
    String email,

    @Schema(description = "Inscrição estadual", example = "12345678909")
    String stateRegistration
) {}
