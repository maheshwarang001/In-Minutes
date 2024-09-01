package org.example.inventory_read_service.entity.inventory.subProductCategory;

import jakarta.persistence.*;
import lombok.*;
import org.example.inventory_read_service.entity.inventory.subcategory.SubCategory;
import org.springframework.context.annotation.Bean;

import java.io.Serializable;
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

    @Version
    @Column(name = "version", columnDefinition = "BIGINT DEFAULT 0", nullable = false)
    private Long version;



    public static void validateSubProductCategory(SubProductCategory subProductCategory) {
        if (subProductCategory == null
                || subProductCategory.getSubproductcategory_id() == null
                || subProductCategory.getSubproductcategory_name() == null
                || subProductCategory.getSubproductcategory_name().isEmpty()
                || subProductCategory.getSubproductcategory_image() == null
                || subProductCategory.getSubproductcategory_image().isEmpty()) {
            throw new NullPointerException("SubProductCategory fields cannot be null or empty");
        }
    }


    public void updateSubProductCategory(SubProductCategory subProductCategory, Optional<SubCategory> optSubCategory) {
        if (subProductCategory.getSubproductcategory_name() != null && !subProductCategory.getSubproductcategory_name().isEmpty()) {
            this.subproductcategory_name = subProductCategory.getSubproductcategory_name().trim().toLowerCase();
        }

        if (subProductCategory.getSubproductcategory_image() != null && !subProductCategory.getSubproductcategory_image().isEmpty()) {
            this.subproductcategory_image = subProductCategory.getSubproductcategory_image();
        }

        if (subProductCategory.isSubproductcategory_active() != this.isSubproductcategory_active()) {
            this.subproductcategory_active = subProductCategory.isSubproductcategory_active();
        }

        if (subProductCategory.getSubcategory() != null) {
            this.subcategory = optSubCategory.orElseThrow(() -> new NoSuchElementException("SubCategory Not found"));
        }
    }
}
