package org.example.rider_service.dto;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.Data;
import org.example.rider_service.entity.Status;

import java.util.UUID;

@Data
public class RiderDto {


    private UUID riderId;

    private String riderFirstName;

    private String riderSecondName;

    private String riderPhoneNumber;


}
