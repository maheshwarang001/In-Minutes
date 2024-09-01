package org.example.order_service.dto;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class UserResponseDto {

    private UUID userId;

    private String email;

    private String phoneNumber;

    private String firstName;

    private String lastName;
}
