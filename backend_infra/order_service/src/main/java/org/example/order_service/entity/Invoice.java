package org.example.order_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long invoiceId;

    private double totalProductCost;

    private double offer;

    private double handlingCost;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "delivery_fee_id")
    private DeliveryFee deliveryFee;

    private double platformFee;

    private double payable;

}
