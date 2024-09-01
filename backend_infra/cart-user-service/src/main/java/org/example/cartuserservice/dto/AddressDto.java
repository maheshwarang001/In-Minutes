package org.example.cartuserservice.dto;

import lombok.Data;
import org.locationtech.jts.geom.Point;

import java.util.UUID;

@Data
public class AddressDto {

    private String address;

    private String city;

    private String postcode;

    private double lat;
    private double log;

}
