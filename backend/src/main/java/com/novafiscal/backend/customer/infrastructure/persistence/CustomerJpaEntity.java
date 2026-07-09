package com.novafiscal.backend.customer.infrastructure.persistence;

import com.novafiscal.backend.customer.domain.model.CustomerStatus;
import com.novafiscal.backend.customer.domain.model.CustomerType;
import com.novafiscal.backend.customer.domain.model.DocumentType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
import java.util.UUID;
import java.util.List;

@Entity
@Table(name = "customer")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerJpaEntity {

    @Id
    private UUID id;

    @Enumerated(EnumType.STRING)
    @Column(name = "customer_type", nullable = false)
    private CustomerType customerType;

    @Column(name = "document_number", unique = true, nullable = false)
    private String documentNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "document_type", nullable = false)
    private DocumentType documentType;

    @Column(name = "legal_name")
    private String legalName;

    @Column(name = "trade_name")
    private String tradeName;

    @Column(name = "phone")
    private String phone;

    @Column(name = "email")
    private String email;

    @Column(name = "state_registration")
    private String stateRegistration;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private CustomerStatus status;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @Column(name = "updated_at")
    private Instant updatedAt;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AddressJpaEntity> addresses;
}
