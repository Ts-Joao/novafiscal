package com.novafiscal.backend.customer.mapper;

import com.novafiscal.backend.customer.api.dto.AddAddressRequestDTO;
import com.novafiscal.backend.customer.api.dto.AddressResponseDTO;
import com.novafiscal.backend.customer.api.dto.CreateCustomerRequestDTO;
import com.novafiscal.backend.customer.api.dto.CustomerResponseDTO;
import com.novafiscal.backend.customer.domain.model.Address;
import com.novafiscal.backend.customer.domain.model.Customer;
import com.novafiscal.backend.customer.domain.model.Document;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
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

    AddressResponseDTO toResponse(Address address);
}
