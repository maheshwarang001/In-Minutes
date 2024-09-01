package org.example.cartservice.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AddressDto {

    private String address;

    private String city;

    private String postcode;

    private double lat;

    private double log;

}
