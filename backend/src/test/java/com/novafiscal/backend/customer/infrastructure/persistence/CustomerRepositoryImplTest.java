package com.novafiscal.backend.customer.infrastructure.persistence;

import com.novafiscal.backend.common.AbstractIntegrationTest;
import com.novafiscal.backend.customer.domain.model.Address;
import com.novafiscal.backend.customer.domain.model.AddressType;
import com.novafiscal.backend.customer.domain.model.Customer;
import com.novafiscal.backend.customer.domain.model.CustomerType;
import com.novafiscal.backend.customer.domain.model.Document;
import com.novafiscal.backend.customer.domain.model.DocumentType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@Testcontainers
@SpringBootTest
public class CustomerRepositoryImplTest extends AbstractIntegrationTest {

    @Autowired
    CustomerRepositoryImpl customerRepositoryImpl;

    Customer customerA;
    Address address;
    Document document;

    @BeforeEach
    void setUp() {
        address = Address.create(
            AddressType.BILLING,
            "Rua João da Silva",
            "123",
            "casa",
            "Centro",
            "Blumenau",
            "SC",
            "12345-123",
            true);
        document = new Document(
            "80199451924",
            DocumentType.CPF);
        customerA = Customer.create(
            CustomerType.INDIVIDUAL,
            document,
            "José da Silva",
            "Novafiscal",
            "554791234567",
            "jose.silva@example.com",
            null);
    }

    @AfterEach
    void tearDown() {
        customerRepositoryImpl.deleteAll();
    }

    @Nested
    class save {
        @Test
        void shouldPersistCustomer_withValidData() {
            Customer saved = customerRepositoryImpl.save(customerA);

            assertThat(saved.getId()).isEqualTo(customerA.getId());
        }

        @Test
        void shouldPersistCustomer_withAddress() {
            customerA.addAddress(address);
            Customer saved = customerRepositoryImpl.save(customerA);

            assertThat(saved.getId()).isEqualTo(customerA.getId());
            assertThat(saved.getAddresses()).isEqualTo(customerA.getAddresses());
        }
    }

    @Nested
    class findById {
        @Test
        void shouldSuccessfully_whenCustomerExists() {
            Customer saved = customerRepositoryImpl.save(customerA);
            Optional<Customer> result = customerRepositoryImpl.findById(saved.getId());

            assertThat(result).isNotEqualTo(Optional.empty());
            assertThat(result.get().getId()).isEqualTo(customerA.getId());
        }

        @Test
        void shouldReturnEmptyOptional_whenCustomerNotFound() {
            UUID id = UUID.randomUUID();
            Optional<Customer> result = customerRepositoryImpl.findById(id);

            assertThat(result).isEqualTo(Optional.empty());
        }
    }

    @Nested
    class findByDocumentNumber {
        @Test
        void shouldSuccessfully_whenDocumentNumberExists() {
            Customer saved = customerRepositoryImpl.save(customerA);
            Optional<Customer> result = customerRepositoryImpl.findByDocumentNumber(saved.getDocument().number());

            assertThat(result).isNotEqualTo(Optional.empty());
            assertThat(result.get().getId()).isEqualTo(customerA.getId());
        }

        @Test
        void shouldReturnEmptyOptional_whenDocumentNumberNotFound() {
            Optional<Customer> result = customerRepositoryImpl.findByDocumentNumber("12345678901");

            assertThat(result).isEqualTo(Optional.empty());
        }
    }

    @Nested
    class existsByDocumentNumber {
        @Test
        void shouldSuccessfully_whenDocumentNumberExists() {
            Customer saved = customerRepositoryImpl.save(customerA);
            boolean result = customerRepositoryImpl.existsByDocumentNumber(saved.getDocument().number());

            assertThat(result).isTrue();
        }

        @Test
        void shouldReturnFalse_whenDocumentNumberNotFound() {
            boolean result = customerRepositoryImpl.existsByDocumentNumber("12345678901");

            assertThat(result).isFalse();
        }
    }
}
