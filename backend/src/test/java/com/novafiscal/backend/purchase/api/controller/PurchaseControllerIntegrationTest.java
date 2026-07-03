package com.novafiscal.backend.purchase.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class PurchaseControllerIntegrationTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Nested
    class createPurchase {

        @Test
        void shouldReturn201_whenPayloadIsValid() throws Exception {
            String payload = """
                {
                    "customerName": "João Teixeira",
                    "items": [
                        { "description": "Item A", "price": 20, "quantity": 2 }
                    ]
                }
                """;

            mockMvc.perform(post("/purchases")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(payload))
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.data.customerName").value("João Teixeira"))
                    .andExpect(jsonPath("$.data.totalAmount").value(40));
        }

        @Test
        void shouldReturn400WithDomainException_whenItemsListIsEmpty() throws Exception {
            String payload = """
                {
                    "customerName": "João Teixeira",
                    "items": []
                }
                """;

            mockMvc.perform(post("/purchases")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(payload))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.error").value("Business Rule Violation"))
                    .andExpect(jsonPath("$.path").value("/purchases"));
        }

        @Test
        void shouldReturn400WithValidationErrors_whenCustomerNameIsMissing() throws Exception {
            String payload = """
                {
                    "items": [
                        { "description": "Item A", "price": 20, "quantity": 2 }
                    ]
                }
                """;

            mockMvc.perform(post("/purchases")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(payload))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.error").value("Validation Failed"))
                    .andExpect(jsonPath("$.errors[0].field").value("customerName"));
        }
    }
}
