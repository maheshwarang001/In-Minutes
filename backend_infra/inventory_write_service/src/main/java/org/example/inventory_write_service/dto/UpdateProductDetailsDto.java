package org.example.inventory_write_service.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.Optional;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UpdateProductDetailsDto {

    private Optional<String> productDescription;

    private Optional<String> unit;

    private Optional<String> metaData;

    private Optional<String> productCountryOfOrigin;
}
