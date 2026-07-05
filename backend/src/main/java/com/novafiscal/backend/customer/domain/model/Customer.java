package com.novafiscal.backend.customer.domain.model;

import com.novafiscal.backend.customer.domain.exception.CustomerAlreadyInactiveException;
import lombok.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Builder(toBuilder = true)
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

    public void generateIdentifier() {
        this.id = UUID.randomUUID();
    }

    public void markAsCreated() {
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
        this.addresses.add((address));
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
        return this.legalName != null && this.legalName.isBlank()
                && this.addresses != null && this.addresses.isEmpty();
    }
}
