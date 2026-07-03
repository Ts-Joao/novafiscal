package com.novafiscal.backend.common.validation;

public record ValidationError(
        String field,
        String message
) {}
