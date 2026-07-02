package com.novafiscal.backend.common.validation;

import java.time.Instant;
import java.util.List;

public record ValidationErrorResponse(
        Instant timestamp,
        int status,
        String error,
        List<ValidationError> errors
) {}
