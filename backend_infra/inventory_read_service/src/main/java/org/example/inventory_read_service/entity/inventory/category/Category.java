package org.example.inventory_read_service.entity.inventory.category;

import jakarta.persistence.*;
import lombok.*;
import org.example.inventory_read_service.dto.CategoryDto;
import org.example.inventory_read_service.dto.CategoryUpdateDto;
import org.example.inventory_read_service.entity.inventory.subcategory.SubCategory;

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

    // Method to validate category fields before performing operations
    public static  void validateCategoryUpdate(CategoryDto categoryDto) throws Exception {
       if(categoryDto == null || categoryDto.getCategoryId() == null) throw  new NullPointerException();
    }


    public static void validateCategoryCreate(CategoryDto categoryDto) throws Exception {

        if (categoryDto == null || categoryDto.getCategoryId() == null || categoryDto.getCategoryName() == null || categoryDto.getCategoryName().name().isEmpty()
                || categoryDto.getCategoryImage() == null || categoryDto.getCategoryImage().isEmpty()
        ) {
            throw new NullPointerException("Invalid Category Data");
        }
    }

    // Method to create a Category from a CategoryDto
    public static Category fromCategoryDto(CategoryDto categoryDto) {
        return Category.builder()
                .category_id(categoryDto.getCategoryId())
                .category_name(categoryDto.getCategoryName())
                .category_image(categoryDto.getCategoryImage())
                .active(categoryDto.isActive())
                .build();
    }
    public void updateCategory(Category category) {
        if (category.getCategory_name() != null) {
            this.category_name = category.getCategory_name();
        }
        if (category.getCategory_image() != null) {
            this.category_image = category.getCategory_image();
        }
        if (category.isActive() != this.active) {
            this.active = category.isActive();
        }
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
}
