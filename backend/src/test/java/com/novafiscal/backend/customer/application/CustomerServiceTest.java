package com.novafiscal.backend.customer.application;

import com.novafiscal.backend.common.exception.ResourceNotFoundException;
import com.novafiscal.backend.customer.api.dto.UpdateContactInfoRequestDTO;
import com.novafiscal.backend.customer.domain.exception.DuplicatedCustomerException;
import com.novafiscal.backend.customer.domain.model.*;
import com.novafiscal.backend.customer.domain.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @Mock
    CustomerRepository customerRepository;

    @InjectMocks
    CustomerService customerService;

    Customer customerA;

    @BeforeEach
    void setUp() {
        customerA = Customer.create(
                CustomerType.INDIVIDUAL,
                new Document("08710839090", DocumentType.CPF),
                "Pedro Silva",
                "Novafiscal",
                "5547992340987",
                "pedrosilva@example.com",
                null);
    }

    @Nested
    class Create {

        @Test
        void shouldSaveCustomer_whenDocumentNumberDoesNotExist() {
            when(customerRepository.existsByDocumentNumber(customerA.getDocument().number()))
                    .thenReturn(false);
            when(customerRepository.save(any(Customer.class))).thenReturn(customerA);

            Customer result = customerService.create(customerA);

            assertThat(result).isEqualTo(customerA);
            verify(customerRepository).save(customerA);
        }

        @Test
        void shouldThrowException_whenEmailAlreadyExists() {
            when(customerRepository.existsByEmail(customerA.getEmail()))
                    .thenReturn(true);

            assertThatThrownBy(() -> customerService.create(customerA))
                    .isInstanceOf(DuplicatedCustomerException.class);

            verify(customerRepository, never()).save(any());
        }

        @Test
        void shouldThrowException_whenDocumentNumberAlreadyExists() {
            when(customerRepository.existsByDocumentNumber(customerA.getDocument().number()))
                    .thenReturn(true);

            assertThatThrownBy(() -> customerService.create(customerA))
                    .isInstanceOf(DuplicatedCustomerException.class);

            verify(customerRepository, never()).save(any());
        }
    }

    @Nested
    class FindById {

        @Test
        void shouldReturnCustomer_whenIdExists() {
            when(customerRepository.findById(customerA.getId()))
                    .thenReturn(Optional.of(customerA));

            Customer result = customerService.findById(customerA.getId());

            assertThat(result).isEqualTo(customerA);
        }

        @Test
        void shouldThrowException_whenIdDoesNotExist() {
            UUID randomId = UUID.randomUUID();
            when(customerRepository.findById(randomId))
                    .thenReturn(Optional.empty());

            assertThatThrownBy(() -> customerService.findById(randomId))
                    .isInstanceOf(ResourceNotFoundException.class);
        }
    }

    @Nested
    class Deactivate {

        @Test
        void shouldDeactivateAndSaveCustomer_whenCustomerIsActive() {
            when(customerRepository.findById(customerA.getId()))
                    .thenReturn(Optional.of(customerA));
            when(customerRepository.save(any(Customer.class))).thenReturn(customerA);

            Customer result = customerService.deactivate(customerA.getId());

            assertThat(result.getStatus()).isEqualTo(CustomerStatus.INACTIVE);
            verify(customerRepository).save(customerA);
        }

        @Test
        void shouldThrowException_whenCustomerDoesNotExist() {
            UUID randomId = UUID.randomUUID();
            when(customerRepository.findById(randomId))
                    .thenReturn(Optional.empty());

            assertThatThrownBy(() -> customerService.deactivate(randomId))
                    .isInstanceOf(ResourceNotFoundException.class);

            verify(customerRepository, never()).save(any());
        }
    }

    @Nested
    class Activate {

        @Test
        void shouldActivateAndSaveCustomer_whenCustomerIsInactive() {
            customerA.deactivate();
            when(customerRepository.findById(customerA.getId()))
                    .thenReturn(Optional.of(customerA));
            when(customerRepository.save(any(Customer.class))).thenReturn(customerA);

            Customer result = customerService.activate(customerA.getId());

            assertThat(result.getStatus()).isEqualTo(CustomerStatus.ACTIVE);
            verify(customerRepository).save(customerA);
        }
    }

    @Nested
    class UpdateContactInfo {

        @Test
        void shouldUpdateAndSaveCustomer_whenCustomerExists() {
            when(customerRepository.findById(customerA.getId()))
                    .thenReturn(Optional.of(customerA));
            when(customerRepository.save(any(Customer.class))).thenReturn(customerA);

            UpdateContactInfoRequestDTO dto = new UpdateContactInfoRequestDTO(
                    "11999998888", "novo@example.com");

            Customer result = customerService.updateContactInfo(
                    customerA.getId(), dto);

            assertThat(result.getPhone()).isEqualTo("11999998888");
            assertThat(result.getEmail()).isEqualTo("novo@example.com");
            verify(customerRepository).save(customerA);
        }

        @Test
        void shouldThrowException_whenCustomerDoesNotExist() {
            UUID randomId = UUID.randomUUID();
            when(customerRepository.findById(randomId))
                    .thenReturn(Optional.empty());

            UpdateContactInfoRequestDTO dto = new UpdateContactInfoRequestDTO(
                    "11999998888", "novo@example.com");

            assertThatThrownBy(() ->
                    customerService.updateContactInfo(randomId, dto))
                    .isInstanceOf(ResourceNotFoundException.class);

            verify(customerRepository, never()).save(any());
        }
    }

    @Nested
    class AddAddress {

        @Test
        void shouldAddAddressAndSaveCustomer_whenCustomerExists() {
            Address address = Address.create(
                    AddressType.BILLING, "Rua João da Silva", "123", "Casa",
                    "Centro", "Blumenau", "SC", "12345-123", true);

            when(customerRepository.findById(customerA.getId()))
                    .thenReturn(Optional.of(customerA));
            when(customerRepository.save(any(Customer.class))).thenReturn(customerA);

            Customer result = customerService.addAddress(customerA.getId(), address);

            assertThat(result.getAddresses()).contains(address);
            verify(customerRepository).save(customerA);
        }

        @Test
        void shouldThrowException_whenCustomerDoesNotExist() {
            UUID randomId = UUID.randomUUID();
            Address address = Address.create(
                    AddressType.BILLING, "Rua João da Silva", "123", "Casa",
                    "Centro", "Blumenau", "SC", "12345-123", true);

            when(customerRepository.findById(randomId))
                    .thenReturn(Optional.empty());

            assertThatThrownBy(() -> customerService.addAddress(randomId, address))
                    .isInstanceOf(ResourceNotFoundException.class);

            verify(customerRepository, never()).save(any());
        }
    }
}