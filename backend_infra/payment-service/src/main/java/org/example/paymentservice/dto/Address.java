package org.example.paymentservice.dto;


import lombok.Data;

@Data

public class Address {
    private long addressId;

    private String address;

    private String city;

    private String postcode;

    private double lat;
    private double log;

}
