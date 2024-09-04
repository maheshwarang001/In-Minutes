package org.example.cartuserservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Product {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long tableId;

    private UUID productId;


    private int qty;

    @ManyToOne
    @JoinColumn(name = "cart_id")
    private Cart cart;


}
