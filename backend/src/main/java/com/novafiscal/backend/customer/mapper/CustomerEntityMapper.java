package com.novafiscal.backend.customer.mapper;

import com.novafiscal.backend.customer.domain.model.Address;
import com.novafiscal.backend.customer.domain.model.Customer;
import com.novafiscal.backend.common.domain.model.Document;
import com.novafiscal.backend.customer.infrastructure.persistence.AddressJpaEntity;
import com.novafiscal.backend.customer.infrastructure.persistence.CustomerJpaEntity;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface CustomerEntityMapper {

    @Mapping(target = "documentNumber", source = "document.number")
    @Mapping(target = "documentType", source = "document.type")
    @Mapping(target = "addresses", source = "addresses")
    CustomerJpaEntity toEntity(Customer customer);

        default Customer toDomain(CustomerJpaEntity entity) {
        return Customer.reconstitute(
                entity.getId(),
                entity.getCustomerType(),
                new Document(entity.getDocumentNumber(), entity.getDocumentType()),
                entity.getLegalName(),
                entity.getTradeName(),
                entity.getPhone(),
                entity.getEmail(),
                entity.getStateRegistration(),
                entity.getStatus(),
                entity.getAddresses().stream().map(this::toDomain).collect(java.util.stream.Collectors.toList()),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }

    @Mapping(target = "customer", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "isDefault", source = "default")
    AddressJpaEntity toEntity(Address address);

    default Address toDomain(AddressJpaEntity entity) {
        return Address.reconstitute(
                entity.getId(),
                entity.getType(),
                entity.getStreet(),
                entity.getNumber(),
                entity.getComplement(),
                entity.getNeighborhood(),
                entity.getCity(),
                entity.getState(),
                entity.getZipCode(),
                entity.isDefault()
        );
    }

    @AfterMapping
    default void linkAddressesToCustomer(@MappingTarget CustomerJpaEntity entity) {
        if (entity.getAddresses() != null) {
            entity.getAddresses().forEach(address -> address.setCustomer(entity));
        }
    }
}