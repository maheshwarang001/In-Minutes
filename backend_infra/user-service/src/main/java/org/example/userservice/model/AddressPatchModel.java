package org.example.userservice.model;

import lombok.Data;

import java.util.UUID;

@Data
public class AddressPatchModel {
    private UUID uuid;
    private String address;
    private String town;
    private String postcode;
}
