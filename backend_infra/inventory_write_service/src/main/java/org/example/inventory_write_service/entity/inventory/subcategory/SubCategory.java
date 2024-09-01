package org.example.inventory_write_service.entity.inventory.subcategory;

import jakarta.persistence.*;
import lombok.*;
import org.example.inventory_write_service.dto.CategoryDto;
import org.example.inventory_write_service.dto.SubCategoryC;
import org.example.inventory_write_service.dto.SubCategoryDto;
import org.example.inventory_write_service.dto.SubProductCategoryC;
import org.example.inventory_write_service.entity.inventory.category.Category;
import org.example.inventory_write_service.entity.inventory.subProductCategory.SubProductCategory;

import java.io.Serializable;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "subcategory_table")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SubCategory implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "subcategory_id", nullable = false, updatable = false)
    private UUID subcategory_id;

    @Column(name = "subcategory_name", nullable = false)
    private String subcategory_name;

    @Column(name = "subcategory_image", nullable = false)
    private String subcategory_image;

    @Column(name = "subcategory_active")
    private boolean subcategory_active;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;


    @Version
    @Column(name = "version", columnDefinition = "BIGINT DEFAULT 0", nullable = false)
    private Long version;



    public SubCategory(String subcategory_name, String subcategory_image, boolean subcategory_active, Category category) {
        this.subcategory_name = subcategory_name.trim().toLowerCase();
        this.subcategory_image = subcategory_image;
        this.subcategory_active = subcategory_active;
        this.category = category;
    }




    @Override
    public String toString() {
        return "SubCategory{" +
                "subcategory_id=" + subcategory_id +
                ", subcategory_name='" + subcategory_name + '\'' +
                ", subcategory_image='" + subcategory_image + '\'' +
                ", subcategory_active=" + subcategory_active +
                ", category=" + category +
                ", version=" + version +
                '}';
    }

    public void updateSubCategory(SubCategoryC subCategory, Optional<Category> newCategory) {



        if (subCategory.getSubcategory_name() != null && !subCategory.getSubcategory_name().isEmpty()) {
            this.subcategory_name = subCategory.getSubcategory_name().trim().toLowerCase();
        }
        if (subCategory.getSubcategory_image() != null && !subCategory.getSubcategory_image().isEmpty()) {
            this.subcategory_image = subCategory.getSubcategory_image();
        }
        if (subCategory.isSubcategory_active() != this.subcategory_active) {
            this.subcategory_active = subCategory.isSubcategory_active();
        }

        if (subCategory.getCategoryId() != null) {
            if (newCategory.isPresent()) {
                this.category = newCategory.get();
            } else {
                throw new NoSuchElementException("Category not found");
            }
        }
    }
}
