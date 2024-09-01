package org.example.inventory_write_service.dto;

import lombok.Data;

@Data
public class StoreC {


    private String store_name;

    private double latitude;

    private double longitude;


    private boolean active;

    //private LocalDateTime doj;
}
