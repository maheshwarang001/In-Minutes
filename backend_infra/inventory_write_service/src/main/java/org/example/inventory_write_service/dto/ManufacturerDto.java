package org.example.inventory_write_service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.inventory_write_service.entity.inventory.product.Manufacturer;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ManufacturerDto {

    private UUID manufacturerId;

    private String manufacturerName;

    private String manufacturerAddress;

    public Manufacturer generateManufacturerFromDtoUpdate(ManufacturerDto manufacturerDto){

        return Manufacturer
                .builder()
                .manufacturer_id(manufacturerDto.getManufacturerId())
                .manufacturer_name(manufacturerDto.getManufacturerName())
                .manufacturer_address(manufacturerDto.getManufacturerAddress())
                .build();

    }

    public Manufacturer generateManufacturerFromCreate(ManufacturerDto manufacturerDto){
        return Manufacturer
                .builder()
                .manufacturer_name(manufacturerDto.getManufacturerName())
                .manufacturer_address(manufacturerDto.getManufacturerAddress())
                .build();
    }


    public static ManufacturerDto generateManufacturerDtoFromObject(Manufacturer manufacturer){

        return ManufacturerDto
                .builder()
                .manufacturerId(manufacturer.getManufacturer_id())
                .manufacturerName(manufacturer.getManufacturer_name())
                .manufacturerAddress(manufacturer.getManufacturer_address())
                .build();

    }
}
