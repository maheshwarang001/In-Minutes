package org.example.userservice.entity.inventroy;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@Table
public class ProductCost {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "product_cost_id",nullable = false)
    UUID product_cost_id;

    @Column(name = "mrp",nullable = false)
    double mrp;

    @Column(name = "offer")
    double offer;

    @Column(name = "offer_valid", nullable = false)
    LocalDateTime offer_valid;

}
