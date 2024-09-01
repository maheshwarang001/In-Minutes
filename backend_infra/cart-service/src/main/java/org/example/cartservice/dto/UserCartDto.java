package org.example.cartservice.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class UserCartDto {

    private UUID userId;

    private UUID productId;

    private int qty;

    private UUID storeId;


}
