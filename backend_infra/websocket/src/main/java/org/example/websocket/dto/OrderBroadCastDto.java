package org.example.websocket.dto;

import lombok.Builder;
import lombok.Data;
import org.locationtech.jts.geom.Point;

import java.io.Serializable;
import java.util.UUID;

@Builder
@Data
public class OrderBroadCastDto implements Serializable {

    private UUID orderId;


    private double lat;

    private double log;

    private double deliveryCompensation;

    private double customerTip;

    private double bonus;

    private double handlingCost;

    private double totalPay;

    private double avgTotalDistance;

    @Override
    public String toString() {
        return "OrderBroadCastDto{" +
                "orderId=" + orderId +
                ", lat=" + lat +
                ", log=" + log +
                ", deliveryCompensation=" + deliveryCompensation +
                ", customerTip=" + customerTip +
                ", bonus=" + bonus +
                ", handlingCost=" + handlingCost +
                ", totalPay=" + totalPay +
                ", avgTotalDistance=" + avgTotalDistance +
                '}';
    }
}
