package org.example.inventory_write_service.entity.inventory.product;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.UUID;


@Getter
@Setter
@Entity
@Table(name = "product_details_table")
@AllArgsConstructor
@NoArgsConstructor
public class ProductDetails implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "product_details_id", nullable = false)
    private UUID productDetails_id;

    @Column(name = "product_description")
    private String product_description;

    @Column(name = "product_unit")
    private String unit;

    @Column(name = "product_meta_data")
    private String meta_data;

    @Column(name = "product_country_of_origin", nullable = false)
    private String county_origin;

    @Version
    @Column(name = "version", columnDefinition = "BIGINT DEFAULT 0", nullable = false)
    private Long version;

    public ProductDetails(String product_description, String unit, String meta_data, String county_origin) {
        this.product_description = product_description;
        this.unit = unit;
        this.meta_data = meta_data;
        this.county_origin = county_origin;
    }
}
