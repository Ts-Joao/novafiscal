package com.novafiscal.backend.customer.domain.model;

import lombok.*;

import java.util.UUID;

@Getter
@Builder(toBuilder = true, access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Address {

    private UUID id;
    private AddressType type;
    private String street;
    private String number;
    private String complement;
    private String neighborhood;
    private String city;
    private String state;
    private String zipCode;
    private boolean isDefault;

    public static Address create(AddressType type, String street, String number, String complement,
                String neighborhood, String city, String state, String zipCode, boolean isDefault) {
        return Address.builder()
                .id(UUID.randomUUID())
                .type(type)
                .street(street)
                .number(number)
                .complement(complement)
                .neighborhood(neighborhood)
                .city(city)
                .state(state)
                .zipCode(zipCode)
                .isDefault(isDefault)
                .build();
    }

    public static Address reconstitute(UUID id, AddressType type, String street, String number, String complement,
                String neighborhood, String city, String state, String zipCode, boolean isDefault) {
        return Address.builder()
                .id(id)
                .type(type)
                .street(street)
                .number(number)
                .complement(complement)
                .neighborhood(neighborhood)
                .city(city)
                .state(state)
                .zipCode(zipCode)
                .isDefault(isDefault)
                .build();
    }

    void markAsDefault() {
        this.isDefault = true;
    }

    void unmarkAsDefault() {
        this.isDefault = false;
    }

    public void update(String street, String number, String complement, String neighborhood,
                        String city, String state, String zipCode) {
        this.street = street;
        this.number = number;
        this.complement = complement;
        this.neighborhood = neighborhood;
        this.city = city;
        this.state = state;
        this.zipCode = zipCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Address address)) return false;
        return id != null && id.equals(address.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
