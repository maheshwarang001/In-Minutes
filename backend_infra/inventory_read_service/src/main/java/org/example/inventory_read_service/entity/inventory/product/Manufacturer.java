package org.example.inventory_read_service.entity.inventory.product;

import jakarta.persistence.*;
import lombok.*;
import org.example.inventory_read_service.dto.ManufacturerDto;

import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "manufacturer_table")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Manufacturer implements Serializable {

    @Id
    @Column(name = "manufacturer_id", nullable = false)
    private UUID manufacturer_id;

    @Column(name = "manufacturer_name", nullable = false)
    private String manufacturer_name;

    @Column(name = "manufacturer_address", nullable = false)
    private String manufacturer_address;

    @Version
    @Column(name = "version", columnDefinition = "BIGINT DEFAULT 0", nullable = false)
    private Long version;

    public Manufacturer(UUID uuid , String manufacturer_name, String manufacturer_address) {
        this.manufacturer_id = uuid;
        this.manufacturer_name = manufacturer_name;
        this.manufacturer_address = manufacturer_address;
    }



    public static Manufacturer generateManufacturerFromDto(ManufacturerDto manufacturerDto){

        return Manufacturer
                .builder()
                .manufacturer_id(manufacturerDto.getManufacturerId())
                .manufacturer_name(manufacturerDto.getManufacturerName())
                .manufacturer_address(manufacturerDto.getManufacturerAddress())
                .build();

    }

    public static void validManufacturerCreate(Manufacturer manufacturer){
        if(manufacturer == null || manufacturer.getManufacturer_id() == null || manufacturer.getManufacturer_name() == null  ||  manufacturer.getManufacturer_address() == null || manufacturer.getManufacturer_address().isEmpty() || manufacturer.getManufacturer_name().isEmpty() ){
            throw new NullPointerException("Manufacturer");
        }
    }

    public void updateManufacturer(Manufacturer manufacturer){

        if(manufacturer.getManufacturer_id() != null){
            this.manufacturer_id = manufacturer.getManufacturer_id();
        }
        if(manufacturer.getManufacturer_name() != null && !manufacturer.getManufacturer_name().isEmpty()){
            this.manufacturer_name = manufacturer.getManufacturer_name().trim().toLowerCase();
        }
        if(manufacturer.getManufacturer_address() != null && !manufacturer.getManufacturer_address().isEmpty()){
            this.manufacturer_address = manufacturer.getManufacturer_address();
        }
    }
}
