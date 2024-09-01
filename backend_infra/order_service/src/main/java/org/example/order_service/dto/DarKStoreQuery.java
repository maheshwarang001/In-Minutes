package org.example.order_service.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class DarKStoreQuery {


    private UUID darkStoreId;
    private List<ItemDetailsDto> items;

}
