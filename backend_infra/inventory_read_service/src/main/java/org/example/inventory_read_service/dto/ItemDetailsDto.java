package org.example.inventory_read_service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ItemDetailsDto {

    private UUID productId;

    private String productName;

    private String productImage;

    private double productCost;

    private int qty;

    private double totalProductCost;

}
