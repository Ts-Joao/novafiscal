package com.novafiscal.backend.customer.mapper;

import com.novafiscal.backend.customer.api.dto.*;
import com.novafiscal.backend.customer.domain.model.Address;
import com.novafiscal.backend.customer.domain.model.Customer;
import com.novafiscal.backend.common.domain.model.Document;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;


@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface CustomerMapper {

    default Customer toDomain(CreateCustomerRequestDTO dto) {
        Document document = new Document(dto.documentNumber(), dto.documentType());
        return Customer.create(
                dto.customerType(),
                document,
                dto.legalName(),
                dto.tradeName(),
                dto.phone(),
                dto.email(),
                dto.stateRegistration()
        );
    }

    default Address toDomain(AddAddressRequestDTO dto) {
        return Address.create(
                dto.type(),
                dto.street(),
                dto.number(),
                dto.complement(),
                dto.neighborhood(),
                dto.city(),
                dto.state(),
                dto.zipCode(),
                dto.isDefault()
        );
    }

    CustomerResponseDTO toResponse(Customer customer);

    @Mapping(target = "isDefault", source = "default")
    AddressResponseDTO toResponse(Address address);

    DocumentResponseDTO toResponse(Document document);
}
