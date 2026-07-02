package com.novafiscal.backend.purchase.domain.model;

import lombok.*;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Purchase {

    private UUID id;
    private String customerName;

    private List<PurchaseItem> items;
}
