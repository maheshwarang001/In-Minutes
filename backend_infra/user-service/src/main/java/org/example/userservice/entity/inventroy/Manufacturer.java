package org.example.userservice.entity.inventroy;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Data
@Entity
@Table(name = "manufacturer_table")
public class Manufacturer {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "manufacturer_id", nullable = false)
    UUID manufacturer_id;

    @Column(name = "manufacturer_name", nullable = false)
    String manufacturer_name;

    @Column(name = "manufacturer_address", nullable = false)
    String manufacturer_address;

}
