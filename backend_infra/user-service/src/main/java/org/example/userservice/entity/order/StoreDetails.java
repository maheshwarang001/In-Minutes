package org.example.userservice.entity.order;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StoreDetails {

    private UUID store_id;
    private String store_name;

    private long latitude;

    private long longitude;
}
