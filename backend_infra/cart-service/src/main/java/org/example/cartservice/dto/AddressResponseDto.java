package org.example.cartservice.dto;

import lombok.Builder;
import lombok.Data;
import org.example.cartservice.entity.Address;

@Data
@Builder
public class AddressResponseDto {

    private long addressId;

    private String address;

    private String city;

    private String postcode;

    private double lat;

    private double log;

}
