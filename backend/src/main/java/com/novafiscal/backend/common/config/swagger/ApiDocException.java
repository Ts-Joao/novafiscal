package com.novafiscal.backend.common.config.swagger;

import com.novafiscal.backend.common.response.ApiErrorResponse;
import com.novafiscal.backend.common.validation.ValidationErrorResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@ApiResponses(value = {
    @ApiResponse(
        responseCode = "400",
        description = "Erro de validação nos campos enviados",
        content = {
            @Content(schema = @Schema(implementation = ValidationErrorResponse.class)),
            @Content(schema = @Schema(implementation = ApiErrorResponse.class))
        }
    ),
    @ApiResponse(
        responseCode = "404",
        description = "Recurso não encontrado",
        content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))
    ),
    @ApiResponse(
        responseCode = "500",
        description = "Erro interno no servidor",
        content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))
    )
})
public @interface ApiDocException {
}
