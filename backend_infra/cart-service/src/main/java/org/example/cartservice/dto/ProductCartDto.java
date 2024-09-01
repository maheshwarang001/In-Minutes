package org.example.cartservice.dto;

import jakarta.persistence.Entity;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class ProductCartDto {

    private UUID productId;

    private String productName;

    private String productImage;

    private double productCost;
}
