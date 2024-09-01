package org.example.inventory_write_service.entity.inventory.subProductCategory;

import jakarta.persistence.*;
import lombok.*;
import org.example.inventory_write_service.dto.SubProductCategoryC;
import org.example.inventory_write_service.entity.inventory.product.Product;
import org.example.inventory_write_service.entity.inventory.subcategory.SubCategory;

import java.io.Serializable;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "subproduct_category_table")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SubProductCategory implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "subproductcategory_id", nullable = false, updatable = false)
    private UUID subproductcategory_id;

    @Column(name = "subproductcategory_name", nullable = false)
    private String subproductcategory_name;

    @Column(name = "subproductcategory_image", nullable = false)
    private String subproductcategory_image;

    @Column(name = "subproductcategory_active")
    private boolean subproductcategory_active;

    @ManyToOne
    @JoinColumn(name = "subcategory_id", nullable = false)
    private SubCategory subcategory;

//    @OneToMany(mappedBy = "subProductCategory", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    private List<Product> products;

    @Version
    @Column(name = "version", columnDefinition = "BIGINT DEFAULT 0", nullable = false)
    private Long version;


    public SubProductCategory(String subproductcategory_name, String subproductcategory_image, boolean subproductcategory_active, SubCategory subcategory) {
        this.subproductcategory_name = subproductcategory_name.trim().toLowerCase();
        this.subproductcategory_image = subproductcategory_image;
        this.subproductcategory_active = subproductcategory_active;
        this.subcategory = subcategory;
    }

    public void updateSubCategory(SubProductCategoryC subProductCategoryC, Optional<SubCategory> existingSubProductCategory) throws Exception {

        if (subProductCategoryC.getSubProductCategoryImage() != null && !subProductCategoryC.getSubProductCategoryImage().isEmpty()) {
           this.subproductcategory_image = subProductCategoryC.getSubProductCategoryImage();
        }

        if (subProductCategoryC.getSubProductCategoryName() != null && !subProductCategoryC.getSubProductCategoryName().isEmpty()) {
           this.subproductcategory_name = subProductCategoryC.getSubProductCategoryName().trim().toLowerCase();
        }

        if (subProductCategoryC.isSubProductCategoryActive() != this.subproductcategory_active) {
            this.subproductcategory_active = subProductCategoryC.isSubProductCategoryActive();
        }

        if (subProductCategoryC.getSubCategoryId() != null) {
            if (existingSubProductCategory.isPresent()) {
                this.subcategory = existingSubProductCategory.get();
            }
            else{
                throw new NoSuchElementException("SubCategory not found");
            }
        }
    }
}
