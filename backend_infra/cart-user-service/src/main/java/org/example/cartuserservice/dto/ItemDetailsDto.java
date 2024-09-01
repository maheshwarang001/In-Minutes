package org.example.cartuserservice.dto;

import lombok.Data;

import java.util.UUID;


@Data
public class ItemDetailsDto {

    private UUID productId;

    private String productName;

    private String productImage;

    private double productCost;

    private int qty;

    private double totalProductCost;

}
