package org.example.inventory_read_service.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DarkStoreDto {

    private UUID store_ID;

    private String store_name;

    private double latitude;

    private double longitude;


    private boolean active;





}
