package org.example.rider_service.dto;

import lombok.Data;
import org.example.rider_service.entity.Status;

import java.util.UUID;

@Data
public class StatusChange {

    private UUID userId;

    private Status status;
}
