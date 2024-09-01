package org.example.rider_service.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import org.locationtech.jts.geom.Point;

import java.util.UUID;


@Data
@Entity
@Builder
public class RiderLocation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "geography(Point, 4326)")
    private Point location;

    @OneToOne(mappedBy = "riderLocation")
    private Rider rider;


    @Version
    private long version;

    public RiderLocation() {
    }



}