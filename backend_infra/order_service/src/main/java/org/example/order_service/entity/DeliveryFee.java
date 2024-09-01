package org.example.order_service.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryFee {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long deliveryFeeId;

    private double deliveryFee;

    private double tip;
}
