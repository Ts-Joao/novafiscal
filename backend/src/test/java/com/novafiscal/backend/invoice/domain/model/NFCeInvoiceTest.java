package com.novafiscal.backend.invoice.domain.model;

import com.novafiscal.backend.customer.domain.exception.InvalidDocumentException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.util.UUID;

class NFCeInvoiceTest {

    NFCeInvoice nfceInvoiceA;

    @BeforeEach
    void setUp() {
        nfceInvoiceA = NFCeInvoice.create(
                UUID.randomUUID(),
                UUID.randomUUID(),
                BigDecimal.valueOf(100),
                "08710839090",
                PaymentMethod.PIX,
                BigDecimal.TEN
        );
    }

    @Test
    void shouldCreateNFCeInvoiceSuccessfully_whenCalled() {
        assertNotNull(nfceInvoiceA);
        assertEquals("08710839090", nfceInvoiceA.getCustomerCpf());
    }

    @Test
    void shouldThrowException_whenCustomerCpfIsNull() {
        assertThrows(InvalidDocumentException.class, () -> NFCeInvoice.create(
                UUID.randomUUID(),
                UUID.randomUUID(),
                BigDecimal.valueOf(100),
                "",
                PaymentMethod.PIX,
                BigDecimal.TEN
        ));
    }

    @Test
    void shouldThrowException_whenCustomerCpfIsNotValid() {
        assertThrows(InvalidDocumentException.class, () -> NFCeInvoice.create(
                UUID.randomUUID(),
                UUID.randomUUID(),
                BigDecimal.valueOf(100),
                "123.456.789-0A",
                PaymentMethod.PIX,
                BigDecimal.TEN
        ));
    }
}
