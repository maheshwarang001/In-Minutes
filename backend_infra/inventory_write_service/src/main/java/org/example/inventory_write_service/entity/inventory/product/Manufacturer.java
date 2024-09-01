package org.example.inventory_write_service.entity.inventory.product;

import jakarta.persistence.*;
import lombok.*;

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
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "manufacturer_id", nullable = false)
    private UUID manufacturer_id;

    @Column(name = "manufacturer_name", nullable = false)
    private String manufacturer_name;

    @Column(name = "manufacturer_address", nullable = false)
    private String manufacturer_address;

    @Version
    @Column(name = "version", columnDefinition = "BIGINT DEFAULT 0", nullable = false)
    private Long version;

    public Manufacturer(String manufacturer_name, String manufacturer_address) {
        this.manufacturer_name = manufacturer_name;
        this.manufacturer_address = manufacturer_address;
    }
}
