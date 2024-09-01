package org.example.inventory_read_service.entity.store;

import jakarta.persistence.*;
import lombok.*;
import org.example.inventory_read_service.dto.DarkStoreDto;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "dark_store_table")
public class DarkStore  implements Serializable {

    @Id
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


    public static DarkStore generateDarkStoreByDto(DarkStoreDto darkStoreDto){

        return  DarkStore.builder()
                .store_ID(darkStoreDto.getStore_ID())
                .store_name(darkStoreDto.getStore_name())
                .latitude(darkStoreDto.getLatitude())
                .longitude(darkStoreDto.getLongitude())
                .active(darkStoreDto.isActive())
                .build();
    }

}
