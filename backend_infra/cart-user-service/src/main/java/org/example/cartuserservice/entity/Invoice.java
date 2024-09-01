package org.example.cartuserservice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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

    private double totalPayable;

    public Invoice(){

    }

}
