package org.example.inventory_read_service.dto;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class CartOrderItemDto {

    private UUID productId;

    private int qty;

}
