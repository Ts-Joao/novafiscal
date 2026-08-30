package com.novafiscal.backend.customer.domain.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.UUID;

public class AddressTest {

    @Test
    void shouldCreateAddress_withValidData() {
        Address address = Address.create(
            AddressType.BILLING,
            "Rua João da Silva",
            "123",
            "casa",
            "Centro",
            "Blumenau",
            "SC",
            "12345-123",
            true);
        assertNotNull(address);
        assertEquals(AddressType.BILLING, address.getType());
        assertEquals("Rua João da Silva", address.getStreet());
        assertEquals("123", address.getNumber());
        assertEquals("Centro", address.getNeighborhood());
        assertEquals("12345-123", address.getZipCode());
        assertEquals("SC", address.getState());
        assertEquals("Blumenau", address.getCity());
        assertTrue(address.isDefault());
    }

    @Test
    void shouldReconstituteAddress_withValidData() {
        Address address = Address.reconstitute(
            UUID.randomUUID(),
            AddressType.BILLING,
            "Rua João da Silva",
            "123",
            "casa",
            "Centro",
            "Blumenau",
            "SC",
            "12345-123",
            true);
        assertNotNull(address);
        assertEquals(AddressType.BILLING, address.getType());
        assertEquals("Rua João da Silva", address.getStreet());
        assertEquals("123", address.getNumber());
        assertEquals("Centro", address.getNeighborhood());
        assertEquals("12345-123", address.getZipCode());
        assertEquals("SC", address.getState());
        assertEquals("Blumenau", address.getCity());
        assertTrue(address.isDefault());
    }

    @Test
    void shouldUpdateAddress_withValidData() {
        Address address = Address.create(
            AddressType.BILLING,
            "Rua João da Silva",
            "123",
            "casa",
            "Centro",
            "Blumenau",
            "SC",
            "12345-123",
            true);
        address.update(
            "Rua João da Silva",
            "123",
            "casa",
            "Centro",
            "Blumenau",
            "SC",
            "12345-123");
        assertNotNull(address);
        assertEquals(AddressType.BILLING, address.getType());
        assertEquals("Rua João da Silva", address.getStreet());
        assertEquals("123", address.getNumber());
        assertEquals("Centro", address.getNeighborhood());
        assertEquals("12345-123", address.getZipCode());
        assertEquals("SC", address.getState());
        assertEquals("Blumenau", address.getCity());
    }
}
