package org.example.inventory_read_service.dto;

import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class CartOrderCheckList {

    private UUID storeId;

    List<CartOrderItemDto> cartOrderItemDtoList;
}
