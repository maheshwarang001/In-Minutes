package org.example.inventory_read_service.entity.store;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;


@Data
@Entity
@Table(name = "dark_store_table")
public class DarkStore {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "store_id",nullable = false)
    private UUID store_ID;

    @Column(name = "store_name",nullable = false)
    private String store_name;

    @Column(name = "store_latitude", nullable = false)
    private float latitude;

    @Column(name = "store_longitude", nullable = false)
    private float longitude;

    @Column(name = "store_active")
    private boolean active;

    @Column(name = "store_doj", nullable = false,updatable = false)
    private LocalDateTime doj;

}
