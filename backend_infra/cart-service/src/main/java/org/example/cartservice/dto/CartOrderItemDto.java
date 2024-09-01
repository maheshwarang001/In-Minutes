package org.example.cartservice.dto;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class CartOrderItemDto {

    private UUID productId;

    private int qty;

}
