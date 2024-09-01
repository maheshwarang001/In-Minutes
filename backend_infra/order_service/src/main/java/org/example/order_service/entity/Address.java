package org.example.order_service.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import org.locationtech.jts.geom.Point;

@Data
@Entity
public class Address {


    @Id
    private long addressId;

    private String address1;

    private String address2;

    private String city;

    private String postcode;

    private Point point;
}
