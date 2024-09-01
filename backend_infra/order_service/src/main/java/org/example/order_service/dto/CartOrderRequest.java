package org.example.order_service.dto;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class CartOrderRequest {
    private UUID orderId;
    private long referenceNumber;
    private UUID userId;
    private String emailId;
    private String phoneNumber;
    private Address address;
    private String firstName;
    private String lastName;
    private double payable;
}
