package org.example.inventory_write_service.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.Optional;
import java.util.UUID;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UpdateProductDto {

    private Optional<String> productName;

    private Optional<String> productPicture;

    private Optional<UpdateProductDetailsDto> productDetails;

    private Optional<UpdateProductCost> productCost;

    private Optional<Boolean> isActive;

    private Optional<UUID> productManufacturerId;

    private Optional<UUID> subProductCategoryId;
}


