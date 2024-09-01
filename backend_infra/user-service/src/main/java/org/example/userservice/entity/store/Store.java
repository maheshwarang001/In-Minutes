package org.example.userservice.entity.store;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Data
@Entity
@Table(name = "store_table")
public class Store {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "store_id", nullable = false)
    UUID store_id;

    @Column(name = "store_name", nullable = false)
    String store_name;

    @Column(name = "store_address", nullable = false)
    String store_address;

    @Column(name = "store_latitude", nullable = false)
    long store_latitude;

    @Column(name = "store_longitude", nullable = false)
    long store_longitude;

}
