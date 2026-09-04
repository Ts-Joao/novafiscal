package com.novafiscal.backend.invoice.domain.model;

import com.novafiscal.backend.common.domain.model.Document;
import com.novafiscal.backend.common.domain.model.DocumentType;
import com.novafiscal.backend.invoice.domain.exceptions.InvoiceAlreadyAuthorizedException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.util.UUID;

class InvoiceTest {

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

    @Nested
    class Submit {

        @Test
        void shouldSubmitSuccessfully_whenCalled() {
            assertDoesNotThrow(invoiceA::submit);
            assertEquals(InvoiceStatus.SUBMITTED, invoiceA.getStatus());
        }

        @Test
        void shouldThrowException_whenInvoiceIsNotPending() {
            NFeInvoice invoiceB = NFeInvoice.builder()
                    .status(InvoiceStatus.AUTHORIZED)
                    .build();

            assertThrows(IllegalStateException.class, invoiceB::submit);
        }
    }

    @Nested
    class Authorize {
        @Test
        void shouldAuthorizeSuccessfully_whenCalled() {
            NFeInvoice invoiceB = NFeInvoice.builder()
                    .status(InvoiceStatus.SUBMITTED)
                    .build();

            assertDoesNotThrow(() -> invoiceB.authorize("123", "123"));
            assertEquals("123",invoiceB.getProtocolNumber());
            assertEquals("123", invoiceB.getAccessKey());
            assertNotNull(invoiceB.authorizedAt);
            assertEquals(InvoiceStatus.AUTHORIZED, invoiceB.getStatus());
        }

        @Test
        void shouldThrowException_whenInvoiceIsNotSubmitted() {
            assertThrows(IllegalStateException.class, () -> invoiceA.authorize("123", "123"));
        }

        @Test
        void shouldThrowException_whenInvoiceIsAlreadyAuthorized() {
            NFeInvoice invoiceB = NFeInvoice.builder()
                    .status(InvoiceStatus.AUTHORIZED)
                    .build();

            assertThrows(InvoiceAlreadyAuthorizedException.class, () -> invoiceB.authorize("123", "123"));
        }
    }

    @Nested
    class Reject {
        @Test
        void shouldRejectSuccessfully_whenCalled() {
            NFeInvoice invoiceB = NFeInvoice.builder()
                    .status(InvoiceStatus.SUBMITTED)
                    .build();

            assertDoesNotThrow(invoiceB::reject);
            assertEquals(InvoiceStatus.REJECTED, invoiceB.getStatus());
        }

        @Test
        void shouldThrowException_whenInvoiceIsNotSubmitted() {
            assertThrows(IllegalStateException.class, invoiceA::reject);
        }
    }

    @Nested
    class Cancel {
        @Test
        void shouldCancelSuccessfully_whenCalled() {
            NFeInvoice invoiceB = NFeInvoice.builder()
                    .status(InvoiceStatus.AUTHORIZED)
                    .build();

            assertDoesNotThrow(invoiceB::cancel);
            assertEquals(InvoiceStatus.CANCELED, invoiceB.getStatus());
            assertNotNull(invoiceB.canceledAt);
        }

        @Test
        void shouldThrowException_whenInvoiceIsNotAuthorized() {
            assertThrows(IllegalStateException.class, invoiceA::cancel);
        }
    }
}
