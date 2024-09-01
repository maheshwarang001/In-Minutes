package org.example.cartuserservice.dto;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;


@Data
@Builder
public class ProductDetailDto {

    private UUID productId;

    private String productName;

    private String productImageUrl;


}
