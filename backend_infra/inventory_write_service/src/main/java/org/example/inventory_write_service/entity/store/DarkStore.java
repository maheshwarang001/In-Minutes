package org.example.inventory_write_service.entity.store;

import jakarta.persistence.*;
import lombok.*;
import org.example.inventory_write_service.dto.DarkStoreDto;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;


@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "dark_store_table")
public class DarkStore implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "store_id",nullable = false)
    private UUID store_ID;

    @Column(name = "store_name",nullable = false)
    private String store_name;

    @Column(name = "store_latitude", nullable = false)
    private double latitude;

    @Column(name = "store_longitude", nullable = false)
    private double longitude;

    @Column(name = "store_active")
    private boolean active;

    @Column(name = "store_doj", nullable = false,updatable = false)
    private LocalDateTime doj;

//    @OneToMany(mappedBy = "store", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    private List<StoreProduct> storeProducts;

    @Version
    @Column(name = "version", columnDefinition = "BIGINT DEFAULT 0", nullable = false)
    private Long version;

    public static DarkStoreDto generateDtoByObject(DarkStore darkStore){
        return DarkStoreDto
                .builder()
                .store_ID(darkStore.getStore_ID())
                .store_name(darkStore.getStore_name())
                .latitude(darkStore.getLatitude())
                .longitude(darkStore.getLongitude())
                .active(darkStore.isActive())
                .build();
    }
}
