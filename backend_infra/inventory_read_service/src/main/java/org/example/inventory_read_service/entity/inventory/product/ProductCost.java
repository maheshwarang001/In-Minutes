package org.example.inventory_read_service.entity.inventory.product;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table
@NoArgsConstructor
@AllArgsConstructor
public class ProductCost implements Serializable {

    @Id
    @Column(name = "product_cost_id",nullable = false)
    UUID product_cost_id;

    @Column(name = "mrp",nullable = false)
    double mrp;

    @Column(name = "offer",nullable = true)
    double offer;

    @Column(name = "offer_valid",nullable = true)
    LocalDateTime offer_valid;

    @Version
    @Column(name = "version", columnDefinition = "BIGINT DEFAULT 0", nullable = false)
    private Long version;


    public ProductCost(double mrp, double offer, LocalDateTime offer_valid) {
        this.mrp = mrp;
        this.offer = offer;
        this.offer_valid = offer_valid;
    }

    public ProductCost(double mrp) {
        this.mrp = mrp;
    }
}
