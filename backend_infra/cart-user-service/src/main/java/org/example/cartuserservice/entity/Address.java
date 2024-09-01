package org.example.cartuserservice.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import org.locationtech.jts.geom.Point;

import java.util.UUID;


@Data
@Entity
@Builder
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long addressId;

    private String address;

    private String city;

    private String postcode;

    private Point customerLocation;

    private UUID nearestStore;


    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

    public Address(){

    }


}
