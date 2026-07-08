package com.novafiscal.backend.customer.api.dto;

import com.novafiscal.backend.customer.domain.model.DocumentType;
import io.swagger.v3.oas.annotations.media.Schema;

public record DocumentResponseDTO(

        @Schema(description = "Número do documento")
        String number,

        @Schema(description = "Tipo do documento")
        DocumentType type
) {}
