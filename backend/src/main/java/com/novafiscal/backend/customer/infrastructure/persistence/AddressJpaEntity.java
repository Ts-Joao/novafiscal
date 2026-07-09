package com.novafiscal.backend.customer.infrastructure.persistence;

import com.novafiscal.backend.customer.domain.model.AddressType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "customer_address")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddressJpaEntity {

    @Id
    private UUID id;

    @Setter
    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private CustomerJpaEntity customer;

    @Enumerated(EnumType.STRING)
    @Column(name = "address_type", nullable = false)
    private AddressType type;

    @Column(name = "street", nullable = false)
    private String street;

    @Column(name = "number")
    private String number;

    @Column(name = "complement")
    private String complement;

    @Column(name = "neighborhood", nullable = false)
    private String neighborhood;

    @Column(name = "city", nullable = false)
    private String city;

    @Column(name = "state", nullable = false)
    private String state;

    @Column(name = "zip_code", nullable = false)
    private String zipCode;

    @Column(name = "is_default", nullable = false)
    @Builder.Default
    private boolean isDefault = false;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;
}
