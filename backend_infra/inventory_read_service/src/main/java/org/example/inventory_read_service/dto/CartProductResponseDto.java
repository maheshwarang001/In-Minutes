package org.example.inventory_read_service.dto;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;


@Data
@Builder
public class CartProductResponseDto {

    private UUID id;

    private int qty;

    private double cost;

}
