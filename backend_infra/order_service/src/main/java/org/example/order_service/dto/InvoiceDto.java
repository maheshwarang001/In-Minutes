package org.example.order_service.dto;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class InvoiceDto {

    private double totalProductCost;

    private double offer;

    private double handlingCost;

    private double deliveryFee;

    private double platformFee;

    private double totalPayable;

    private double tip;


}
