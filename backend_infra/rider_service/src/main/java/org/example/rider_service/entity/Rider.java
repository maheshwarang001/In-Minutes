package org.example.rider_service.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import org.example.rider_service.dto.RiderDto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;


@Data
@Entity
@Builder
public class Rider {

    @Id
    private UUID riderId;

    private String riderFirstName;

    private String riderSecondName;

    private String riderPhoneNumber;

    @Enumerated(EnumType.STRING)
    private Status riderStatus;

    private LocalDateTime nextOrder;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "rider_location_id")
    private RiderLocation riderLocation;

    @Version
    private long version;

    public Rider() {

    }


    public static Rider riderBuilderFromDto(RiderDto riderDto){

        return Rider
                .builder()
                .riderId(riderDto.getRiderId())
                .riderFirstName(riderDto.getRiderFirstName())
                .riderSecondName(riderDto.getRiderSecondName())
                .riderPhoneNumber(riderDto.getRiderPhoneNumber())
                .build();
    }


}
