package org.example.cartuserservice.dto;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AddressResponseDto {

    private long addressId;

    private String address;

    private String city;

    private String postcode;
}
