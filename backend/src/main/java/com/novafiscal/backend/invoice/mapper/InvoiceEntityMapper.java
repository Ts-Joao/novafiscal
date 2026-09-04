package com.novafiscal.backend.invoice.mapper;

import com.novafiscal.backend.common.domain.model.Document;
import com.novafiscal.backend.invoice.domain.model.Invoice;
import com.novafiscal.backend.invoice.domain.model.NFCeInvoice;
import com.novafiscal.backend.invoice.domain.model.NFeInvoice;
import com.novafiscal.backend.invoice.infrastructure.persistence.InvoiceJpaEntity;
import com.novafiscal.backend.invoice.infrastructure.persistence.NFCeInvoiceJpaEntity;
import com.novafiscal.backend.invoice.infrastructure.persistence.NFeInvoiceJpaEntity;
import org.mapstruct.*;

@Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface InvoiceEntityMapper {

    @SubclassMapping(source = NFeInvoiceJpaEntity.class, target = NFeInvoice.class)
    @SubclassMapping(source = NFCeInvoiceJpaEntity.class, target = NFCeInvoice.class)
    default Invoice toDomain(InvoiceJpaEntity entity) {
        if (entity == null) {
            return null;
        }

        return switch (entity) {
            case NFeInvoiceJpaEntity nfe -> toDomain(nfe);
            case NFCeInvoiceJpaEntity nfce -> toDomain(nfce);
            default -> throw new IllegalArgumentException("Unsupported entity type");
        };
    }

    default NFeInvoice toDomain(NFeInvoiceJpaEntity entity) {
        return NFeInvoice.reconstitute(
                entity.getId(),
                entity.getCustomerId(),
                entity.getPurchaseId(),
                entity.getTotalAmount(),
                entity.getStatus(),
                entity.getCustomerStateRegistration(),
                new Document(entity.getCustomerDocumentNumber(), entity.getCustomerDocumentType()),
                entity.getOperationNumber(),
                entity.getCfop(),
                entity.getIcmsAmount(),
                entity.getIssuedAt(),
                entity.getAuthorizedAt(),
                entity.getCanceledAt()
        );
    }

    default NFCeInvoice toDomain(NFCeInvoiceJpaEntity entity) {
        return NFCeInvoice.reconstitute(
                entity.getId(),
                entity.getCustomerId(),
                entity.getPurchaseId(),
                entity.getStatus(),
                entity.getTotalAmount(),
                entity.getProtocolNumber(),
                entity.getAccessKey(),
                entity.getPaymentMethod(),
                entity.getChangeAmount(),
                entity.getConsumerCpf(),
                entity.getIssuedAt(),
                entity.getAuthorizedAt(),
                entity.getCanceledAt()
        );
    }

    @SubclassMapping(source = NFeInvoice.class, target = NFeInvoiceJpaEntity.class)
    @SubclassMapping(source = NFCeInvoice.class, target = NFCeInvoiceJpaEntity.class)
    default InvoiceJpaEntity toEntity(Invoice invoice) {
        if (invoice == null) {
            return null;
        }
        return switch (invoice) {
            case NFeInvoice nfe -> toEntity(nfe);
            case NFCeInvoice nfce -> toEntity(nfce);
        };
    }

    @Mapping(target = "customerDocumentNumber", source = "customerDocument.number")
    @Mapping(target = "customerDocumentType", source = "customerDocument.type")
    NFeInvoiceJpaEntity toEntity(NFeInvoice nfeInvoice);

    NFCeInvoiceJpaEntity toEntity(NFCeInvoice nfceInvoice);
}
