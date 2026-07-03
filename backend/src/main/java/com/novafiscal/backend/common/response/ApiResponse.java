package com.novafiscal.backend.common.response;

import java.time.Instant;

public record ApiResponse<T>(
        Instant timestamp,
        T data
) {}
