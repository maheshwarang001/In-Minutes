package org.example.userservice.model;

import jakarta.persistence.Column;
import lombok.Data;

@Data
public class AddressModel {
    private String address;
    private String town;
    private String postcode;
    private long latitude;
    private long longitude;
}
