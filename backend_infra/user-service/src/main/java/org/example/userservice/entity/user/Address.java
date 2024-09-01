package org.example.userservice.entity.user;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Data
@Entity
@Table(name = "address")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "address_id",nullable = false, updatable = false)
    private UUID address_id;

    @Column(name = "address_tag", nullable = false)
    private AddressTag addressTag;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "town", nullable = false)
    private String town;

    @Column(name = "postcode" , nullable = false)
    private String postcode;

    @Column(name = "latitude", nullable = false)
    private long latitude;

    @Column(name = "longitude", nullable = false)
    private long longitude;

}
