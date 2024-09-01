package org.example.inventory_write_service.entity.inventory.category;

import jakarta.persistence.*;
import lombok.*;
import org.example.inventory_write_service.dto.CategoryC;
import org.example.inventory_write_service.dto.CategoryDto;
import org.example.inventory_write_service.entity.inventory.subcategory.SubCategory;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;


@Getter
@Setter
@Entity
@Table(name = "category_table")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Category implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "category_id", nullable = false,updatable = false)
    private UUID category_id;

    @Enumerated(EnumType.STRING)
    @Column(name = "category_name", nullable = false)
    private CategoryEnum category_name;

    @Column(name = "category_image", nullable = false)
    private String category_image;

    //for the future to down any category
    @Column(name = "category_active")
    private boolean active;

    @Version
    @Column(name = "version", columnDefinition = "BIGINT DEFAULT 0", nullable = false)
    private Long version;

    public Category(CategoryEnum category_name, String category_image, boolean active) {
        this.category_name = category_name;
        this.category_image = category_image;
        this.active = active;
    }

    @Override
    public String toString() {
        return "Category{" +
                "category_id=" + category_id +
                ", category_name=" + category_name +
                ", category_image='" + category_image + '\'' +
                ", active=" + active +
                ", version=" + version +
                '}';
    }

    public static void validateCategoryBeforeCreation(CategoryC categoryC) throws Exception {
        if (categoryC == null || categoryC.getCategoryEnum() == null || categoryC.getCategoryEnum().name().isEmpty()
                || categoryC.getCategoryImage() == null || categoryC.getCategoryImage().isEmpty()) {
            throw new NullPointerException("Invalid Input Data");
        }
    }

    public void updateCategory(CategoryDto categoryDto) {
        if (categoryDto.getCategoryName() != null) {
            this.category_name = categoryDto.getCategoryName();
        }
        if (categoryDto.getCategoryImage() != null) {
            this.category_image = categoryDto.getCategoryImage();
        }
        if (categoryDto.isActive() != this.active) {
            this.active = categoryDto.isActive();
        }
    }
}
