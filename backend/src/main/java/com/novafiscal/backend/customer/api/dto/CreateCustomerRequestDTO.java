package com.novafiscal.backend.customer.api.dto;

import com.novafiscal.backend.customer.domain.model.CustomerType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateCustomerRequestDTO(

    @NotNull
    CustomerType customerType,

    @NotBlank
    String documentNumber,

    @NotNull
    String documentType,

    @NotBlank
    String legalName,

    String tradeName,

    String phone,

    @Email
    String email,

    String stateRegistration
) {}
