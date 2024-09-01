package org.example.userservice.entity.inventroy;

import jakarta.persistence.*;
import lombok.Data;
import org.example.userservice.entity.store.StoreProduct;

import java.util.List;
import java.util.UUID;

@Data
@Entity()
@Table(name = "product_table")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "product_id", nullable = false, updatable = false)
    UUID product_id;

    @Column(name = "product_name", nullable = false)
    String product_name;

    @Column(name = "product_description", nullable = false)
    String product_description;

    @Column(name = "product_meta_data")
    String meta_tags;

    @Column(name = "product_origin")
    String product_origin;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "product_cost", nullable = false)
    ProductCost productCost;

    @Column(name = "unit_per_item", nullable = false )
    private String unit_per_item;

    @Column(name = "quantity_in_house", nullable = false)
    private int quantity_in_house;

    @Column(name = "product_image_url")
    private String product_image_url;

    @ManyToOne
    @JoinColumn(name = "manufacturer_id", nullable = false)
    private Manufacturer manufacturer;

    @ManyToOne
    @JoinColumn(name = "sub_category_id", nullable = false)
    private SubCategory subCategory;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<StoreProduct> storeProducts;


}
