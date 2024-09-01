package org.example.cartservice.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import org.locationtech.jts.geom.Point;


@Data
@Entity
@Builder
public class Address {

    @Id
    @GeneratedValue
    private long addressId;

    private String address;

    private String city;

    private String postcode;

    private Point point;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Version
    long version;

    public Address(){

    }
}
