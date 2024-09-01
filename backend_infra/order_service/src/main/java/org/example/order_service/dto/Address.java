package org.example.order_service.dto;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Address {
    private long addressId;

    private String address;

    private String city;

    private String postcode;

    private double lat;
    private double log;

}
