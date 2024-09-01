package org.example.rider_service.service;

import lombok.extern.slf4j.Slf4j;
import org.example.rider_service.dto.RiderLocationDto;
import org.example.rider_service.dto.StatusChange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class RiderLocationExpoService {

    @Autowired
    private RiderLocationService riderLocationService;

    @Async
    public void updateRiderLocation(RiderLocationDto riderLocationDto){

        riderLocationService.updateUserLocation(riderLocationDto);

    }

    @Async
    public void riderStatus(StatusChange statusChange){
        riderLocationService.onCloseUserLocation(statusChange);
    }
}
