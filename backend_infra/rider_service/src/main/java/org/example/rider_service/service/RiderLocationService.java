package org.example.rider_service.service;

import com.netflix.discovery.converters.Auto;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.example.rider_service.dao.LocationDao;
import org.example.rider_service.dao.RiderDao;
import org.example.rider_service.dto.RiderLocationDto;
import org.example.rider_service.dto.StatusChange;
import org.example.rider_service.entity.Rider;
import org.example.rider_service.entity.RiderLocation;
import org.example.rider_service.entity.Status;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
public class RiderLocationService {


    @Autowired
    private RiderDao riderDao;


    @Autowired
    private LocationDao locationDao;



    @Transactional
    public void updateUserLocation(RiderLocationDto riderLocationDto) {

        try {

            Optional<Rider> optionalRider = riderDao.riderCheck(riderLocationDto.getUserId());

            if (optionalRider.isPresent()) {

                Rider rider = optionalRider.get();

                Coordinate coordinate = new Coordinate(riderLocationDto.getLongitude(), riderLocationDto.getLatitude());
                Point riderPoint = new GeometryFactory().createPoint(coordinate);


                if(rider.getRiderStatus() != Status.ONLINE || rider.getRiderStatus() != Status.BUSY )rider.setRiderStatus(Status.ONLINE);

                RiderLocation riderLocation = rider.getRiderLocation();
                if (riderLocation == null) {

                    riderLocation = RiderLocation.builder().location(riderPoint).build();
                    rider.setRiderLocation(riderLocation);

                } else {
                    riderLocation.setLocation(riderPoint);
                }

                riderDao.saveRider(rider);

            }

        }catch (Exception e){
            log.error(e.getLocalizedMessage());
        }

    }

    @Transactional
    public void onCloseUserLocation(StatusChange statusChange){


        try {

            Optional<Rider> optionalRider = riderDao.riderCheck(statusChange.getUserId());

            if (optionalRider.isPresent()) {

                Rider rider = optionalRider.get();

                rider.setRiderStatus(statusChange.getStatus());

                riderDao.saveRider(rider);
            }else throw new NoSuchElementException();

        }catch (Exception e){
            log.error(e.getLocalizedMessage());
        }

    }

    @Transactional
    public void onNetworkFailure(StatusChange statusChange){

        try {

            Optional<Rider> optionalRider = riderDao.riderCheck(statusChange.getUserId());

            if (optionalRider.isPresent()) {

                Rider rider = optionalRider.get();

                rider.setRiderStatus(Status.OUT_OF_NETWORK);

                riderDao.saveRider(rider);
            }else throw new NoSuchElementException();

        }catch (Exception e){
            log.error(e.getLocalizedMessage());
        }

    }


    public CompletableFuture<Rider> findRiderInFixedRadius(Point point , double radius){
        return CompletableFuture.supplyAsync(() -> riderDao.findRiderByPoint(point, radius));
    }




}
