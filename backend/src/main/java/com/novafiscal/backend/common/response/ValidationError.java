package com.novafiscal.backend.common.response;

public record ValidationError(
        String field,
        String message
) {}
