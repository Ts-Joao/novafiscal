package com.novafiscal.backend.customer.api.controller;

import com.jayway.jsonpath.JsonPath;

import jakarta.transaction.Transactional;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class CustomerControllerIntegrationTest {

    @Autowired
    MockMvc mockMvc;

    String customerJson = """
            {
                "customerType": "INDIVIDUAL",
                "documentNumber": "08710839090",
                "documentType": "CPF",
                "legalName": "João Teixeira",
                "tradeName": "Novafiscal",
                "phone": "11987654321",
                "email": "joao.teixeira@example.com",
                "stateRegistration": "123456789012345678"
            }
            """;

    @Nested
    class CreateCustomer {

        @Test
        void shouldReturn201_whenPayloadIsValid() throws Exception {
            mockMvc.perform(post("/customers")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(customerJson))
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.data.legalName").value("João Teixeira"))
                    .andExpect(jsonPath("$.data.email").value("joao.teixeira@example.com"))
                    .andExpect(jsonPath("$.data.document.number").value("08710839090"))
                    .andExpect(jsonPath("$.data.document.type").value("CPF"));
        }

        @Test
        void shouldReturn400WithValidationErrors_whenDocumentIsInvalid() throws Exception {
            String payload = """
                    {
                        "customerType": "INDIVIDUAL",
                        "documentNumber": "123",
                        "documentType": "CPF",
                        "legalName": "João Teixeira",
                        "tradeName": "Novafiscal",
                        "phone": "11987654321",
                        "email": "joao.teixeira@example.com",
                        "stateRegistration": "123456789012345678"
                    }
                    """;

            mockMvc.perform(post("/customers")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(payload))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.error").value("Invalid Document"));
        }

        @Test
        void shouldReturn409WithDomainException_whenDocumentAlreadyExists() throws Exception {
            mockMvc.perform(post("/customers")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(customerJson))
                    .andExpect(status().isCreated());

            mockMvc.perform(post("/customers")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(customerJson))
                    .andExpect(status().isConflict())
                    .andExpect(jsonPath("$.error").value("Duplicated Customer"))
                    .andExpect(jsonPath("$.path").value("/customers"));
        }
    }

    @Nested
    class FindById {

        @Test
        void shouldReturn200_whenCustomerExists() throws Exception {
            MvcResult result = mockMvc.perform(post("/customers")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(customerJson))
                    .andExpect(status().isCreated())
                    .andReturn();

            String responseBody = result.getResponse().getContentAsString();
            String id = JsonPath.read(responseBody, "$.data.id");

            mockMvc.perform(get("/customers/{id}", id))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.data.legalName").value("João Teixeira"))
                    .andExpect(jsonPath("$.data.email").value("joao.teixeira@example.com"))
                    .andExpect(jsonPath("$.data.document.number").value("08710839090"))
                    .andExpect(jsonPath("$.data.document.type").value("CPF"));
        }

        @Test
        void shouldReturn404_whenCustomerDoesNotExist() throws Exception {
            UUID id = UUID.randomUUID();

            mockMvc.perform(get("/customers/{id}", id))
                    .andExpect(status().isNotFound())
                    .andExpect(jsonPath("$.error").value("Resource Not Found"))
                    .andExpect(jsonPath("$.path").value("/customers/" + id));
        }
    }

    @Nested
    class DeactivateCustomer {

        @Test
        void shouldReturn200_whenCustomerExists() throws Exception {
            MvcResult result = mockMvc.perform(post("/customers")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(customerJson))
                    .andExpect(status().isCreated())
                    .andReturn();

            String responseBody = result.getResponse().getContentAsString();
            String id = JsonPath.read(responseBody, "$.data.id");

            mockMvc.perform(patch("/customers/{id}/deactivate", id))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.data.legalName").value("João Teixeira"))
                    .andExpect(jsonPath("$.data.email").value("joao.teixeira@example.com"))
                    .andExpect(jsonPath("$.data.document.number").value("08710839090"))
                    .andExpect(jsonPath("$.data.document.type").value("CPF"));
        }

        @Test
        void shouldReturn404_whenCustomerDoesNotExist() throws Exception {
            UUID id = UUID.randomUUID();

            mockMvc.perform(patch("/customers/{id}/deactivate", id))
                    .andExpect(status().isNotFound())
                    .andExpect(jsonPath("$.error").value("Resource Not Found"))
                    .andExpect(jsonPath("$.path").value("/customers/" + id + "/deactivate"));
        }
    }

    @Nested
    class ActivateCustomer {

        @Test
        void shouldReturn200_whenCustomerExists() throws Exception {
            MvcResult result = mockMvc.perform(post("/customers")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(customerJson))
                    .andExpect(status().isCreated())
                    .andReturn();

            String responseBody = result.getResponse().getContentAsString();
            String id = JsonPath.read(responseBody, "$.data.id");

            mockMvc.perform(patch("/customers/{id}/activate", id))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.data.legalName").value("João Teixeira"))
                    .andExpect(jsonPath("$.data.email").value("joao.teixeira@example.com"))
                    .andExpect(jsonPath("$.data.document.number").value("08710839090"))
                    .andExpect(jsonPath("$.data.document.type").value("CPF"));
        }

        @Test
        void shouldReturn404_whenCustomerDoesNotExist() throws Exception {
            UUID id = UUID.randomUUID();

            mockMvc.perform(patch("/customers/{id}/activate", id))
                    .andExpect(status().isNotFound())
                    .andExpect(jsonPath("$.error").value("Resource Not Found"))
                    .andExpect(jsonPath("$.path").value("/customers/" + id + "/activate"));
        }
    }

    @Nested
    class UpdateContactInfo {

        @Test
        void shouldReturn200_whenCustomerExists() throws Exception {
            MvcResult result = mockMvc.perform(post("/customers")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(customerJson))
                    .andExpect(status().isCreated())
                    .andReturn();

            String responseBody = result.getResponse().getContentAsString();
            String id = JsonPath.read(responseBody, "$.data.id");

            String payload = """
                    {
                        "phone": "11987654321",
                        "email": "joao.teixeira@example.com"
                    }
                    """;

            mockMvc.perform(patch("/customers/{id}/contact-info", id)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(payload))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.data.legalName").value("João Teixeira"))
                    .andExpect(jsonPath("$.data.email").value("joao.teixeira@example.com"))
                    .andExpect(jsonPath("$.data.document.number").value("08710839090"))
                    .andExpect(jsonPath("$.data.document.type").value("CPF"));
        }

        @Test
        void shouldReturn404_whenCustomerDoesNotExist() throws Exception {
            UUID id = UUID.randomUUID();

            String payload = """
                    {
                        "phone": "11987654321",
                        "email": "joao.teixeira@example.com"
                    }
                    """;

            mockMvc.perform(patch("/customers/{id}/contact-info", id)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(payload))
                    .andExpect(status().isNotFound())
                    .andExpect(jsonPath("$.error").value("Resource Not Found"))
                    .andExpect(jsonPath("$.path").value("/customers/" + id + "/contact-info"));
        }
    }

    @Nested
    class AddAddress {

        @Test
        void shouldReturn201_whenCustomerExists() throws Exception {
            MvcResult result = mockMvc.perform(post("/customers")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(customerJson))
                    .andExpect(status().isCreated())
                    .andReturn();

            String responseBody = result.getResponse().getContentAsString();
            String id = JsonPath.read(responseBody, "$.data.id");

            String payload = """
                    {
                        "type": "SHIPPING",
                        "street": "Av. Paulista",
                        "number": "1000",
                        "complement": "Sala 101",
                        "neighborhood": "Jardins",
                        "city": "São Paulo",
                        "state": "SP",
                        "zipCode": "01310100"
                    }
                    """;

            mockMvc.perform(post("/customers/{id}/addresses", id)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(payload))
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.data.legalName").value("João Teixeira"))
                    .andExpect(jsonPath("$.data.email").value("joao.teixeira@example.com"))
                    .andExpect(jsonPath("$.data.document.number").value("08710839090"))
                    .andExpect(jsonPath("$.data.document.type").value("CPF"));
        }

        @Test
        void shouldReturn404_whenCustomerDoesNotExist() throws Exception {
            UUID id = UUID.randomUUID();

            String payload = """
                    {
                        "type": "SHIPPING",
                        "street": "Av. Paulista",
                        "number": "1000",
                        "complement": "Sala 101",
                        "neighborhood": "Jardins",
                        "city": "São Paulo",
                        "state": "SP",
                        "zipCode": "01310100"
                    }
                    """;

            mockMvc.perform(post("/customers/{id}/addresses", id)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(payload))
                    .andExpect(status().isNotFound())
                    .andExpect(jsonPath("$.error").value("Resource Not Found"))
                    .andExpect(jsonPath("$.path").value("/customers/" + id + "/addresses"));
        }
    }
}
