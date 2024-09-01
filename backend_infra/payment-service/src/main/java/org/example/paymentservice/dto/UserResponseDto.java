package org.example.paymentservice.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class UserResponseDto {

    private UUID userId;

    private String email;

    private String phoneNumber;

    private String firstName;

    private String lastName;
}
