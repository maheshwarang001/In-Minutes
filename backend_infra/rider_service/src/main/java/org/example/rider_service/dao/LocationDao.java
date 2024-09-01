package org.example.rider_service.dao;


import org.example.rider_service.dto.RiderLocationDto;
import org.example.rider_service.repository.RiderLocationRepository;
import org.example.rider_service.repository.RiderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LocationDao {

    @Autowired
    private RiderLocationRepository riderLocation;

    @Autowired
    private RiderRepository riderRepository;



    public void update(RiderLocationDto riderLocationDto){


    }



}
