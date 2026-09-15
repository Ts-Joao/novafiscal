package com.novafiscal.backend.invoice.mapper;

import com.novafiscal.backend.common.domain.model.Document;
import com.novafiscal.backend.invoice.api.dto.*;
import com.novafiscal.backend.invoice.domain.model.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.SubclassMapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface InvoiceMapper {

    default NFeInvoice toDomain(CreateNFeInvoiceRequestDTO dto) {
        Document document = new Document(dto.customerDocumentNumber(), dto.customerDocumentType());
        return NFeInvoice.create(
                dto.customerId(),
                dto.purchaseId(),
                dto.totalAmount(),
                dto.customerStateRegistration(),
                document,
                dto.operationNature(),
                dto.cfop(),
                dto.icmsAmount()
        );
    }

    default NFCeInvoice toDomain(CreateNFCeInvoiceRequestDTO dto) {
        return NFCeInvoice.create(
                dto.customerId(),
                dto.purchaseId(),
                dto.totalAmount(),
                dto.consumerCpf(),
                dto.paymentMethod(),
                dto.changeAmount()
        );
    }

    default InvoiceResponseDTO toResponse(Invoice invoice) {
        if (invoice instanceof NFeInvoice nfeInvoice) {
            return toResponse(nfeInvoice);
        }
        if (invoice instanceof NFCeInvoice nfceInvoice) {
            return toResponse(nfceInvoice);
        }
        throw new IllegalArgumentException("Unknown invoice type: " + invoice.getClass());
    }

    @Mapping(target = "invoiceType", constant = "NFE")
    @Mapping(target = "customerDocumentNumber", source = "customerDocument.number")
    @Mapping(target = "customerDocumentType", source = "customerDocument.type")
    @Mapping(target = "consumerCpf", ignore = true)
    @Mapping(target = "operationNature", ignore = true)
    @Mapping(target = "paymentMethod", ignore = true)
    @Mapping(target = "changeAmount", ignore = true)
    InvoiceResponseDTO toResponse(NFeInvoice invoice);

    @Mapping(target = "invoiceType", constant = "NFCE")
    @Mapping(target = "customerDocumentNumber", ignore = true)
    @Mapping(target = "customerDocumentType", ignore = true)
    @Mapping(target = "customerStateRegistration", ignore = true)
    @Mapping(target = "operationNature", ignore = true)
    @Mapping(target = "cfop", ignore = true)
    @Mapping(target = "icmsAmount", ignore = true)
    InvoiceResponseDTO toResponse(NFCeInvoice invoice);
}