package org.example.cartservice.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

@Data
@Entity
@Builder
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long invoiceId;

    private double totalProductCost;

    private double offer;

    private double handlingCost;

    private double deliveryFee;

    private double platformFee;

    public Invoice(){

    }

}
