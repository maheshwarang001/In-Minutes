package org.example.userservice.entity.store;


import jakarta.persistence.*;
import lombok.Data;
import org.example.userservice.entity.inventroy.Product;

import java.util.UUID;

@Data
@Entity
@Table(name = "store_product_table")
public class StoreProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "store_product_id", nullable = false)
    UUID store_product_id;

    @ManyToOne
    @JoinColumn(name = "store_id", nullable = false)
    private Store store;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(name = "store_quantity", nullable = false)
    private int store_quantity;

    @Column(name = "quantity", nullable = false)
    int quantity_sold;


}
