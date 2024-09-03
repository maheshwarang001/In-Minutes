package org.example.inventory_read_service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DarkStoreProductDto {
    UUID darkStore;
    UUID product;
    int qty;
}
