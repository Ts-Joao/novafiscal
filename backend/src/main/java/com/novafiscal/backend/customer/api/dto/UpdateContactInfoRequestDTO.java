package com.novafiscal.backend.customer.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UpdateContactInfoRequestDTO(

    @Schema(description = "Telefone", example = "11999999999")
    @NotBlank
    String phone,

    @Schema(description = "Email", example = "teixeira.joao@example.com")
    @NotBlank @Email
    String email
) {}
