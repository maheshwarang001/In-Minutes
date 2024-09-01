package org.example.rider_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.example.rider_service.entity.RiderLocation;
import org.springframework.stereotype.Repository;

import java.util.UUID;


@Repository

public interface RiderLocationRepository extends JpaRepository<RiderLocation, UUID> {




}
