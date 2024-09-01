package org.example.inventory_write_service.entity.inventory.product;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.inventory_write_service.dto.ItemC;
import org.example.inventory_write_service.dto.ProductDto;
import org.example.inventory_write_service.entity.inventory.subProductCategory.SubProductCategory;
import org.example.inventory_write_service.entity.store.StoreProduct;

import java.io.Serializable;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;


@Data
@Entity
@Table(name = "product_table")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Product implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "product_id", nullable = false, updatable = false)
    private UUID product_id;

    @Column(name = "product_name", nullable = false)
    private String product_name;

    @Column(name = "product_picture", nullable = false)
    private String product_picture;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "product_details_id", referencedColumnName = "product_details_id")
    private ProductDetails product_details;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "product_cost_id", referencedColumnName = "product_cost_id")
    private ProductCost product_cost;

    @Column(name = "product_active")
    private boolean isActive;

    @ManyToOne
    @JoinColumn(name = "manufacturer_id", nullable = false)
    private Manufacturer product_manufacturer;

    @ManyToOne
    @JoinColumn(name = "subproductcategory_id", nullable = false)
    private SubProductCategory subProductCategory;

    @Version
    @Column(name = "version", columnDefinition = "BIGINT DEFAULT 0", nullable = false)
    private Long version;


    public Product(String product_name, String product_picture, ProductDetails product_details, ProductCost product_cost, boolean isActive, Manufacturer product_manufacturer, SubProductCategory subProductCategory) {
        this.product_name = product_name.trim().toLowerCase();
        this.product_picture = product_picture;
        this.isActive = isActive;
        this.product_details = product_details;
        this.product_cost = product_cost;
        this.product_manufacturer = product_manufacturer;
        this.subProductCategory = subProductCategory;
    }

}
