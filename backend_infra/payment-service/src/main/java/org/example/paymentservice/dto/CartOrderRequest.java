package org.example.paymentservice.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class CartOrderRequest {
    private UUID orderId;
    private UUID userId;
    private String emailId;
    private String phoneNumber;
    private Address address;
    private String firstName;
    private String lastName;
    private double payable;
}
