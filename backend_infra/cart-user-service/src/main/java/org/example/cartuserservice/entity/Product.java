package org.example.cartuserservice.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Entity
@Data
@Builder
public class Product {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long tableId;

    private UUID productId;


    private int qty;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "cart_id")
    private Cart cart;

    public Product(){

    }


}
