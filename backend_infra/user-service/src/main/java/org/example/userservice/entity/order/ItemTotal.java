package org.example.userservice.entity.order;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@Entity
@Table(name = "item_totals")
public class ItemTotal {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, name = "item_total_id")
    private UUID itemTotalId;

    private String currency;

    private double total_cost;

    private double taxes;

    private double delivery_charges;

    private double packing_charges;

    private double platform_fee;

    private double grand_total;

    private double total_saved;

}
