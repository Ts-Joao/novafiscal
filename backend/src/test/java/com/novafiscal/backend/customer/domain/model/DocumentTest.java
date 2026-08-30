package com.novafiscal.backend.customer.domain.model;

import com.novafiscal.backend.customer.domain.exception.InvalidDocumentException;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DocumentTest {

    @Test
    void shouldCreateDocument_withValidDocumentTypeAndNumber() {
        Document document = new Document("08710839090", DocumentType.CPF);

        assertEquals(DocumentType.CPF, document.type());
        assertEquals("08710839090", document.number());
    }

    @Test
    void shouldNotCreateDocument_withNullDocumentType() {
        assertThrows(InvalidDocumentException.class, () -> new Document("08710839090", null));
    }

    @Test
    void shouldNotCreateDocument_withInvalidNumber() {
        assertThrows(InvalidDocumentException.class, () -> new Document("0871083909", DocumentType.CPF));
    }

    @Test
    void shouldNotCreateDocument_withNullNumber() {
        assertThrows(InvalidDocumentException.class, () -> new Document(null, DocumentType.CPF));
    }

    @Test
    void shouldNotCreateDocument_withNumberWithInvalidCharacters() {
        assertThrows(InvalidDocumentException.class, () -> new Document("0871083909a", DocumentType.CPF));
    }

    @Test
    void shouldNotCreateDocumentCPF_withLengthShorterThanExpected() {
        assertThrows(InvalidDocumentException.class, () -> new Document("087108390", DocumentType.CPF));
    }

    @Test
    void shouldNotCreateDocumentCPF_withLengthLongerThanExpected() {
        assertThrows(InvalidDocumentException.class, () -> new Document("087108390901", DocumentType.CPF));
    }

    @Test
    void shouldNotCreateDocumentCNPJ_withLengthShorterThanExpected() {
        assertThrows(InvalidDocumentException.class, () -> new Document("087108390", DocumentType.CNPJ));
    }

    @Test
    void shouldNotCreateDocumentCNPJ_withLengthLongerThanExpected() {
        assertThrows(InvalidDocumentException.class, () -> new Document("087108390901213", DocumentType.CNPJ));
    }

    @Test
    void shouldNotCreateDocumentCPF_withAllDigitsEquals() {
        assertThrows(InvalidDocumentException.class, () -> new Document("00000000000", DocumentType.CPF));
    }

    @Test
    void shouldNotCreateDocumentCNPJ_withAllDigitsEquals() {
        assertThrows(InvalidDocumentException.class, () -> new Document("00000000000000", DocumentType.CNPJ));
    }

    @Test
    void shouldSanitizeDocumentNumber_removeNonDigitsCharacters() {
        Document document = new Document("087.108.390-90", DocumentType.CPF);

        assertEquals("08710839090", document.number());
    }
}
