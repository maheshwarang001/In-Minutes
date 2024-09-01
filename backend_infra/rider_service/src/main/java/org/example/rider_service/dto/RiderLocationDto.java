package org.example.rider_service.dto;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;



@Data
@Builder
public class RiderLocationDto {


    private UUID userId;

    private double latitude;

    private double longitude;

}
