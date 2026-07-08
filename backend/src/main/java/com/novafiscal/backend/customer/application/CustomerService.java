package com.novafiscal.backend.customer.application;

import com.novafiscal.backend.common.exception.ResourceNotFoundException;
import com.novafiscal.backend.customer.api.dto.AddAddressRequestDTO;
import com.novafiscal.backend.customer.api.dto.CreateCustomerRequestDTO;
import com.novafiscal.backend.customer.api.dto.UpdateContactInfoRequestDTO;
import com.novafiscal.backend.customer.domain.exception.DuplicatedCustomerException;
import com.novafiscal.backend.customer.domain.model.Address;
import com.novafiscal.backend.customer.domain.model.Customer;
import com.novafiscal.backend.customer.domain.model.Document;
import com.novafiscal.backend.customer.domain.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;

    public Customer create(CreateCustomerRequestDTO request) {
        boolean alreadyExist = customerRepository.existsByDocumentNumber(request.documentNumber());

        if (alreadyExist) {
           throw new DuplicatedCustomerException("Customer already exists with document: " + request.documentNumber());
        }

        Document document = new Document(request.documentNumber(), request.documentType());

        Customer customer = Customer.create(
                request.customerType(),
                document,
                request.legalName(),
                request.tradeName(),
                request.phone(),
                request.email(),
                request.stateRegistration()
        );

        return customerRepository.save(customer);
    }

    public Customer findById(UUID id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found" + id));
    }

    public Customer deactivate(UUID id) {
        Customer customer = findById(id);
        customer.deactivate();
        return customerRepository.save(customer);
    }

    public Customer active(UUID id) {
        Customer customer = findById(id);
        customer.activate();
        return customerRepository.save(customer);
    }

    public Customer updateContactInfo(UUID id, UpdateContactInfoRequestDTO request) {
        Customer customer = findById(id);
        customer.updateContactInfo(request.phone(), request.email());
        return customerRepository.save(customer);
    }

    public Customer addAddress(UUID customerId, AddAddressRequestDTO request) {
        Customer customer = findById(customerId);

        Address address = Address.create(
            request.type(),
            request.street(),
            request.number(),
            request.complement(),
            request.neighborhood(),
            request.city(),
            request.state(),
            request.zipCode(),
            request.isDefault()
        );

        customer.addAddress(address);
        return customerRepository.save(customer);
    }
}
