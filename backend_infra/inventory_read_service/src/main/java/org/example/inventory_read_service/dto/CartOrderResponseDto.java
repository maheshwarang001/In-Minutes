package org.example.inventory_read_service.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class CartOrderResponseDto {

    private List<CartProductResponseDto> responseDto;

    private double totalCost;

}
