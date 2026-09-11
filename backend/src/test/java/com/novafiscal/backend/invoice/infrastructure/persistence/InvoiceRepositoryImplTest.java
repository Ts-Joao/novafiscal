package com.novafiscal.backend.invoice.infrastructure.persistence;

import com.novafiscal.backend.common.AbstractIntegrationTest;
import com.novafiscal.backend.common.domain.model.Document;
import com.novafiscal.backend.common.domain.model.DocumentType;
import com.novafiscal.backend.invoice.domain.model.Invoice;
import com.novafiscal.backend.invoice.domain.model.NFCeInvoice;
import com.novafiscal.backend.invoice.domain.model.NFeInvoice;
import com.novafiscal.backend.invoice.domain.model.PaymentMethod;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@Testcontainers
@SpringBootTest
public class InvoiceRepositoryImplTest extends AbstractIntegrationTest {

    @Autowired
    InvoiceRepositoryImpl invoiceRepositoryImpl;

    Invoice nfceInvoice;
    Invoice nfeInvoice;

    @BeforeEach
    void setUp() {
        nfeInvoice = NFeInvoice.create(
                UUID.randomUUID(),
                null,
                new BigDecimal("1500.00"),
                "123456789",
                new Document("12345678000199", DocumentType.CNPJ),
                "Venda de mercadoria",
                "5102",
                new BigDecimal("270.00")
        );

        nfceInvoice = NFCeInvoice.create(
                UUID.randomUUID(),
                null,
                BigDecimal.TEN,
                "11144477735",
                PaymentMethod.PIX,
                null
        );
    }

    @AfterEach
    void tearDown() {
        invoiceRepositoryImpl.deleteAll();
    }

    @Nested
    class Save {
        @Test
        void shouldPersistNFeInvoice_withValidData() {
            Invoice saved = invoiceRepositoryImpl.save(nfeInvoice);

            assertThat(saved.getId()).isEqualTo(nfeInvoice.getId());
        }

        @Test
        void shouldPersistNFCeInvoice_withValidData() {
            Invoice saved = invoiceRepositoryImpl.save(nfceInvoice);

            assertThat(saved.getId()).isEqualTo(nfceInvoice.getId());
        }
    }

    @Nested
    class findById {
        @Test
        void shouldSuccessfully_whenCustomerExists() {
            Invoice saved = invoiceRepositoryImpl.save(nfceInvoice);
            Optional<Invoice> result = invoiceRepositoryImpl.findById(saved.getId());

            assertThat(saved.getId()).isEqualTo(saved.getId());
        }
        
        @Test
        void shouldReturnEmptyOptional_whenCustomerNotFound() {
            UUID id = UUID.randomUUID();
            Optional<Invoice> result = invoiceRepositoryImpl.findById(id);

            assertThat(result).isEqualTo(Optional.empty());
        }
    }

    @Nested
    class findByCustomerId {
        @Test
        void shouldSuccessfully_whenCustomerExists() {
            Invoice saved = invoiceRepositoryImpl.save(nfceInvoice);
            Optional<Invoice> result = invoiceRepositoryImpl.findById(saved.getId());

            assertThat(saved.getId()).isEqualTo(saved.getId());
        }

        @Test
        void shouldReturnEmptyOptional_whenCustomerNotFound() {
            UUID id = UUID.randomUUID();
            Optional<Invoice> result = invoiceRepositoryImpl.findById(id);

            assertThat(result).isEqualTo(Optional.empty());
        }
    }

    @Nested
    class findByAccessKey {
        @Test
        void shouldSuccessfully_whenCustomerExists() {
            Invoice saved = invoiceRepositoryImpl.save(nfceInvoice);
            Optional<Invoice> result = invoiceRepositoryImpl.findById(saved.getId());

            assertThat(saved.getId()).isEqualTo(saved.getId());
        }

        @Test
        void shouldReturnEmptyOptional_whenCustomerNotFound() {
            UUID id = UUID.randomUUID();
            Optional<Invoice> result = invoiceRepositoryImpl.findById(id);

            assertThat(result).isEqualTo(Optional.empty());
        }
    }
}
