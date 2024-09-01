package org.example.order_service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.locationtech.jts.geom.Point;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DarkStoreResponseDto {

    private UUID storeId;
    private boolean response;

//    private UUID darkStoreId;
//    private String darkStoreName;
//    private Point darkStoreLocation;

}
