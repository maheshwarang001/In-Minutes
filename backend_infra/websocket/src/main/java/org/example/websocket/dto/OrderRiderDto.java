package org.example.websocket.dto;

import lombok.Builder;
import lombok.Data;
import org.locationtech.jts.geom.Point;

import java.util.UUID;

@Data
@Builder
public class OrderRiderDto {

    private UUID orderId;

    private UUID riderId;

    private Point storeLocation;

    private double deliveryCompensation;

    private double customerTip;

    private double bonus;

    private double handlingCost;

    private double totalPay;

    private double avgTotalDistance;

}
