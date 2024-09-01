package org.example.inventory_read_service.dto;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.inventory_read_service.entity.inventory.category.CategoryEnum;

import java.util.UUID;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryUpdateDto {


    private UUID categoryId;

    private CategoryEnum categoryName;
    private String categoryImage;

    private boolean active;





}
