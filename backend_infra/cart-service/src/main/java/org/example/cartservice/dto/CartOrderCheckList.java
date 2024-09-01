package org.example.cartservice.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@Builder
public class CartOrderCheckList {

    private UUID storeId;

    List<CartOrderItemDto> cartOrderItemDtoList;
}
