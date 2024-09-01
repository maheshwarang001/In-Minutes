package org.example.rider_service.repository;

import org.example.rider_service.entity.Rider;
import org.example.rider_service.entity.RiderLocation;
import org.locationtech.jts.geom.Point;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Repository
public interface RiderRepository extends JpaRepository<Rider, UUID> {



    @Query("SELECT rs FROM Rider rs WHERE rs.riderId = :riderId")
    Optional<Rider> getRiderById(@Param("riderId") UUID riderId);



    @Query("SELECT r FROM Rider r WHERE ST_DWithin(r.riderLocation.location, :storePoint, :radius) " +
            "AND r.riderStatus = org.example.rider_service.entity.Status.ONLINE " +
            "AND CURRENT_TIMESTAMP >= r.nextOrder "+
            "ORDER BY ST_Distance(r.riderLocation.location, :storePoint) ASC")
    Rider findNearestRiderInGeoFixedRadius(@Param("storePoint") Point storePoint, @Param("radius") double radius);



}
