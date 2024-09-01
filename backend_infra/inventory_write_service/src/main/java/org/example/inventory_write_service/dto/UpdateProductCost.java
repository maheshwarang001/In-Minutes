package org.example.inventory_write_service.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.Optional;


@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UpdateProductCost {
    private Optional<Double> mrp;

}
