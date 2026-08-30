package com.novafiscal.backend.customer.api.controller;

import com.novafiscal.backend.common.config.swagger.ApiDocException;
import com.novafiscal.backend.common.response.ApiResponse;
import com.novafiscal.backend.customer.api.dto.AddAddressRequestDTO;
import com.novafiscal.backend.customer.api.dto.CreateCustomerRequestDTO;
import com.novafiscal.backend.customer.api.dto.CustomerResponseDTO;
import com.novafiscal.backend.customer.api.dto.UpdateContactInfoRequestDTO;
import com.novafiscal.backend.customer.application.CustomerService;
import com.novafiscal.backend.customer.domain.model.Address;
import com.novafiscal.backend.customer.domain.model.Customer;
import com.novafiscal.backend.customer.mapper.CustomerMapper;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.UUID;

@Tag(name = "Customer", description = "Operações relacionadas ao processamento de clientes")
@RequiredArgsConstructor
@RestController
@RequestMapping("/customers")
@ApiDocException
public class CustomerController {

    private final CustomerService customerService;
    private final CustomerMapper customerMapper;

    @Operation(summary = "Criar um novo cliente", description = "Cria um novo cliente com os dados fornecidos")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "201",
            description = "Cliente criado com sucesso"
        ),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "400",
            description = "Invalid request"
        ),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "409",
            description = "Customer already exists"
        )
    })
    @PostMapping
    public ResponseEntity<ApiResponse<CustomerResponseDTO>> createCustomer(
            @Valid @RequestBody CreateCustomerRequestDTO dto
    ) {

        Customer customer = customerMapper.toDomain(dto);
        Customer created = customerService.create(customer);
        CustomerResponseDTO response = customerMapper.toResponse(created);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ApiResponse<>(Instant.now(), response));
    }

    @Operation(summary = "Busca um cliente pelo seu identificador único")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "200",
            description = "Cliente encontrado com sucesso"
        ),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "404",
            description = "Cliente não encontrado"
        )
    })
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CustomerResponseDTO>> findCustomerById(@PathVariable UUID id) {
        Customer customer = customerService.findById(id);
        CustomerResponseDTO response = customerMapper.toResponse(customer);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ApiResponse<>(Instant.now(), response));
    }

    @Operation(summary = "Desativa um cliente")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "200",
            description = "Cliente desativado com sucesso"
        ),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "404",
            description = "Cliente não encontrado"
        )
    })
    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<ApiResponse<CustomerResponseDTO>> deactivateCustomer(@PathVariable UUID id) {
        Customer customer = customerService.deactivate(id);
        CustomerResponseDTO response = customerMapper.toResponse(customer);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ApiResponse<>(Instant.now(), response));
    }

    @Operation(summary = "Reativa um cliente")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "200",
            description = "Cliente reativado com sucesso"
        ),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "404",
            description = "Cliente não encontrado"
        )
    })
    @PatchMapping("/{id}/activate")
    public ResponseEntity<ApiResponse<CustomerResponseDTO>> activeCustomer(@PathVariable UUID id) {
        Customer customer = customerService.activate(id);
        CustomerResponseDTO response = customerMapper.toResponse(customer);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ApiResponse<>(Instant.now(), response));
    }

    @Operation(summary = "Atualiza as informações de contato de um cliente")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "200",
            description = "Informações de contato do cliente atualizadas com sucesso"
        ),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "404",
            description = "Cliente não encontrado"
        )
    })
    @PatchMapping("/{id}/contact-info")
    public ResponseEntity<ApiResponse<CustomerResponseDTO>> updateContactInfo(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateContactInfoRequestDTO dto) {
        Customer customer = customerService.updateContactInfo(id, dto);
        CustomerResponseDTO response = customerMapper.toResponse(customer);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ApiResponse<>(Instant.now(), response));
    }

    @Operation(summary = "Adiciona um endereço a um cliente")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "200",
            description = "Endereço adicionado com sucesso"
        ),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "404",
            description = "Cliente não encontrado"
        )
    })
    @PostMapping("/{id}/addresses")
    public ResponseEntity<ApiResponse<CustomerResponseDTO>> addAddress(
        @PathVariable UUID id, 
        @Valid @RequestBody AddAddressRequestDTO dto) {

        Address address = customerMapper.toDomain(dto);
        Customer customer = customerService.addAddress(id, address);
        CustomerResponseDTO response = customerMapper.toResponse(customer);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ApiResponse<>(Instant.now(), response));
    }
}
