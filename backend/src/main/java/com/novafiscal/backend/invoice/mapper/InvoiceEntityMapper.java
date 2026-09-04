package com.novafiscal.backend.invoice.mapper;

import com.novafiscal.backend.common.domain.model.Document;
import com.novafiscal.backend.invoice.domain.model.Invoice;
import com.novafiscal.backend.invoice.domain.model.NFCeInvoice;
import com.novafiscal.backend.invoice.domain.model.NFeInvoice;
import com.novafiscal.backend.invoice.infrastructure.persistence.InvoiceJpaEntity;
import com.novafiscal.backend.invoice.infrastructure.persistence.NFCeInvoiceJpaEntity;
import com.novafiscal.backend.invoice.infrastructure.persistence.NFeInvoiceJpaEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.SubclassMapping;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface InvoiceEntityMapper {

    @SubclassMapping(source = NFCeInvoice.class, target = NFCeInvoiceJpaEntity.class)
    @SubclassMapping(source = NFeInvoice.class, target = NFeInvoiceJpaEntity.class)
    Invoice toDomain(InvoiceJpaEntity entity);

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
    };

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
    };

    @SubclassMapping(source = NFCeInvoiceJpaEntity.class, target = NFCeInvoice.class)
    @SubclassMapping(source = NFeInvoiceJpaEntity.class, target = NFeInvoice.class)
    InvoiceJpaEntity toEntity(Invoice invoice);

    NFCeInvoiceJpaEntity toEntity(NFCeInvoice nfceInvoice);
    NFeInvoiceJpaEntity toEntity(NFeInvoice nfeInvoice);
}
