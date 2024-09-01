package org.example.inventory_write_service.entity.store;
import jakarta.persistence.*;
import lombok.*;
import org.example.inventory_write_service.entity.inventory.product.Product;

import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "store_product")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StoreProduct implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "store_product_id", nullable = false, updatable = false)
    private UUID storeProduct_id;

    @ManyToOne
    @JoinColumn(name = "store_id", nullable = false)
    private DarkStore store;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(name = "quantity", nullable = false)
    private int quantity;

    @Column(name = "inStock")
    private boolean inStock;


    @Version
    @Column(name = "version", columnDefinition = "BIGINT DEFAULT 0", nullable = false)
    private Long version;

    public StoreProduct(DarkStore store, Product product, int quantity, boolean inStock) {
        this.store = store;
        this.product = product;
        this.quantity = quantity;
        this.inStock = inStock;
    }
}
