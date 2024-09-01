package org.example.cartuserservice.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

@Entity
@Data
@Builder
public class Cart {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long cartId;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "invoice_id")
    private Invoice invoice;

    public Cart(){

    }



}
