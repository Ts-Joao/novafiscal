package com.novafiscal.backend.customer.domain.model;

import com.novafiscal.backend.customer.domain.exception.CustomerAlreadyInactiveException;
import lombok.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Builder(toBuilder = true, access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Customer {

    private UUID id;
    private CustomerType customerType;
    private Document document;
    private String legalName;
    private String tradeName;
    private String phone;
    private String email;
    private String stateRegistration;
    private CustomerStatus status;
    private List<Address> addresses;
    private Instant createdAt;
    private Instant updatedAt;

    public static Customer create(CustomerType customerType, Document document, String legalName,
                                   String tradeName, String phone, String email, String stateRegistration) {
        Customer customer = Customer.builder()
                .customerType(customerType)
                .document(document)
                .legalName(legalName)
                .tradeName(tradeName)
                .phone(phone)
                .email(email)
                .stateRegistration(stateRegistration)
                .status(CustomerStatus.ACTIVE)
                .addresses(new ArrayList<>())
                .build();

        customer.generateIdentifier();
        customer.markAsCreated();

        return customer;
    }

    public static Customer reconstitute(UUID id, CustomerType customerType, Document document, String legalName,
                                         String tradeName, String phone, String email, String stateRegistration,
                                         CustomerStatus status, List<Address> addresses,
                                         Instant createdAt, Instant updatedAt) {
        return Customer.builder()
                .id(id)
                .customerType(customerType)
                .document(document)
                .legalName(legalName)
                .tradeName(tradeName)
                .phone(phone)
                .email(email)
                .stateRegistration(stateRegistration)
                .status(status)
                .addresses(addresses)
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .build();
    }

    private void generateIdentifier() {
        this.id = UUID.randomUUID();
    }

    private void markAsCreated() {
        this.createdAt = Instant.now();
    }

    public void deactivate() {
        if (this.status == CustomerStatus.INACTIVE) {
            throw new CustomerAlreadyInactiveException("Customer is already inactive");
        }
        this.status = CustomerStatus.INACTIVE;
    }

    public void activate() {
        this.status = CustomerStatus.ACTIVE;
    }

    public void addAddress(Address address) {
        if (this.addresses == null) {
            this.addresses = new ArrayList<>();
        }
        if (address.isDefault()) {
            this.addresses.forEach(Address::unmarkAsDefault);
        }
        this.addresses.add(address);
    }

    public void updateContactInfo(String phone, String email) {
        this.phone = phone;
        this.email = email;
        this.updatedAt = Instant.now();
    }

    public boolean isActive() {
        return this.status == CustomerStatus.ACTIVE;
    }

    public boolean hasCompleteRegistration() {
        return this.legalName != null && !this.legalName.isBlank()
                && this.addresses != null && !this.addresses.isEmpty();
    }
}