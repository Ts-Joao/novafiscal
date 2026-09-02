package com.novafiscal.backend.invoice.domain.model;

import com.novafiscal.backend.customer.domain.exception.InvalidDocumentException;
import com.novafiscal.backend.customer.domain.model.Document;
import com.novafiscal.backend.customer.domain.model.DocumentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.util.UUID;

class NFeInvoiceTest {

    NFeInvoice invoiceA;

    @BeforeEach
    void setUp() {
        invoiceA = NFeInvoice.create(
                UUID.randomUUID(),
                UUID.randomUUID(),
                BigDecimal.TEN,
                "SP",
                new Document("08710839090", DocumentType.CPF),
                "001",
                "123",
                BigDecimal.TEN
        );
    }

    @Test
    void shouldCreateNfeInvoiceSuccessfully_whenCalled() {
        assertNotNull(invoiceA);
    }

    @Test
    void shouldThrowException_whenCustomerStateRegistrationIsNull() {
        assertThrows(InvalidDocumentException.class, () -> NFeInvoice.create(
                UUID.randomUUID(),
                UUID.randomUUID(),
                BigDecimal.TEN,
                "",
                new Document("08710839090", DocumentType.CPF),
                "001",
                "123",
                BigDecimal.TEN
        ));
    }

    @Test
    void shouldThrowException_whenCustomerStateRegistrationDoesNotExist() {
        assertThrows(InvalidDocumentException.class, () -> NFeInvoice.create(
                UUID.randomUUID(),
                UUID.randomUUID(),
                BigDecimal.TEN,
                "ts",
                new Document("08710839090", DocumentType.CPF),
                "001",
                "123",
                BigDecimal.TEN
        ));
    }
}
