package com.novafiscal.backend.customer.application;

import com.novafiscal.backend.common.exception.ResourceNotFoundException;
import com.novafiscal.backend.customer.api.dto.UpdateContactInfoRequestDTO;
import com.novafiscal.backend.customer.domain.exception.DuplicatedCustomerException;
import com.novafiscal.backend.customer.domain.model.Address;
import com.novafiscal.backend.customer.domain.model.Customer;
import com.novafiscal.backend.customer.domain.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;

    public Customer create(Customer customer) {
        boolean alreadyExist = customerRepository.existsByDocumentNumber(customer.getDocument().number());

        if (alreadyExist) {
           throw new DuplicatedCustomerException("Customer already exists with document: " + customer.getDocument().number());
        }

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

    public Customer activate(UUID id) {
        Customer customer = findById(id);
        customer.activate();
        return customerRepository.save(customer);
    }

    public Customer updateContactInfo(UUID id, UpdateContactInfoRequestDTO request) {
        Customer customer = findById(id);
        customer.updateContactInfo(request.phone(), request.email());
        return customerRepository.save(customer);
    }

    public Customer addAddress(UUID customerId, Address address) {
        Customer customer = findById(customerId);
        customer.addAddress(address);
        return customerRepository.save(customer);
    }
}
