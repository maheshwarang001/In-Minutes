package org.example.order_service.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.locationtech.jts.geom.Point;

import java.util.List;
import java.util.UUID;

@Data
@Entity
public class Customer {

    @Id
    private UUID customerId;

    private String customerFirstName;

    private String customerLastName;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "customer_id")
    private List<Address> address;


}
