package org.example.inventory_read_service.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class InvoiceProduct {

    private double totalProductCost;
}
