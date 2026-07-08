package com.novafiscal.backend.customer.api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UpdateContactInfoRequestDTO(

    @NotBlank
    String phone,

    @NotBlank @Email
    String email
) {}
