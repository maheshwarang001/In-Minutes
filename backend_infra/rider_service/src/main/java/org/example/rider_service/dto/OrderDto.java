package org.example.rider_service.dto;

import lombok.Builder;
import lombok.Data;
import org.locationtech.jts.geom.Point;

import java.util.UUID;

@Data
@Builder
public class OrderDto {

    private UUID orderId;

    private Point storeLocation;

    private double deliveryCompensation;

    private double customerTip;

    private double bonus;

    private double handlingCost;

    private double totalPay;

    private double avgTotalDistance;

}
