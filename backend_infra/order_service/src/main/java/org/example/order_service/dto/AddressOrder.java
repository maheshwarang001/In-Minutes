package org.example.order_service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.locationtech.jts.geom.Point;

import java.util.UUID;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddressOrder {

    private long addressId;

    private String address;

    private String city;

    private String postcode;

    private Point customerLocation;

    private UUID nearestStore;

}
