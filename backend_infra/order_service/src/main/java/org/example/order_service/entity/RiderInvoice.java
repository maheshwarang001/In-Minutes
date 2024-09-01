package org.example.order_service.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Entity
public class RiderInvoice {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long riderInvoiceId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "delivery_fee_id")
    private DeliveryFee deliveryFee;

    private double bonus;

    private double totalPay;

    private double avgTotalDistance;

    public RiderInvoice() {

    }
}
