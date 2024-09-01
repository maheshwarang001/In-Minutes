package org.example.inventory_read_service.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.inventory_read_service.entity.inventory.product.Manufacturer;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ManufacturerDto {

    private UUID manufacturerId;

    private String manufacturerName;

    private String manufacturerAddress;


}
