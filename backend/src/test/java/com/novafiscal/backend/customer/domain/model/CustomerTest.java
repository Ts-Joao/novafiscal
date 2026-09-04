package com.novafiscal.backend.customer.domain.model;

import com.novafiscal.backend.common.domain.model.Document;
import com.novafiscal.backend.common.domain.model.DocumentType;
import com.novafiscal.backend.customer.domain.exception.CustomerAlreadyInactiveException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;

public class CustomerTest {

  Customer customerA;

  @BeforeEach
  void setUp() {
    customerA = Customer.create(
        CustomerType.INDIVIDUAL,
        new Document("08710839090", DocumentType.CPF),
        "Pedro Silva",
        null,
        "5547992340987",
        "pedrosilva@example.com",
        null);
  }

  @Test
  public void shouldCreateCustomer_withValidData() {
    Document document = new Document("08710839090", DocumentType.CPF);

    assertNotNull(customerA);
    assertEquals(CustomerType.INDIVIDUAL, customerA.getCustomerType());
    assertEquals(document, customerA.getDocument());
    assertEquals("Pedro Silva", customerA.getLegalName());
    assertEquals("pedrosilva@example.com", customerA.getEmail());
    assertEquals("5547992340987", customerA.getPhone());
  }

  @Test
  public void shouldDeactivateCustomer_whenIsActive() {
    customerA.deactivate();
    assertEquals(CustomerStatus.INACTIVE, customerA.getStatus());
  }

  @Test
  public void shouldThrowException_whenDeactivateCustomerAlreadyDeactivated() {
    customerA.deactivate();
    assertThrows(CustomerAlreadyInactiveException.class, () -> customerA.deactivate());
  }

  @Test
  public void shouldActivateCustomer_whenIsDeactivated() {
    customerA.deactivate();
    customerA.activate();
    assertEquals(CustomerStatus.ACTIVE, customerA.getStatus());
  }

  @Test
  public void shouldAddAddress_whenHasNoAddress() {
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

    customerA.addAddress(address);
    assertEquals(address, customerA.getAddresses().getFirst());
  }

  @Test
  public void shouldUpdateCustomer_whenHasValidData() {
    customerA.updateContactInfo(
        "11992340987", "silva.pedro@example.com");
    assertEquals("11992340987", customerA.getPhone());
    assertEquals("silva.pedro@example.com", customerA.getEmail());
  }

  @Test
  public void shouldCheckIfCustomerIsActive() {
    assertTrue(customerA.isActive());
  }

  @Test
  public void shouldHasCompleteRegistration_whenHasAllFields() {
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

    Customer customerB = Customer.create(
        CustomerType.INDIVIDUAL,
        new Document("08710839090", DocumentType.CPF),
        "Pedro Silva",
        "Novafiscal",
        "5547992340987",
        "silvadepedro@example.com",
        null);

    customerB.addAddress(address);
    assertTrue(customerB.hasCompleteRegistration());
  }
}
