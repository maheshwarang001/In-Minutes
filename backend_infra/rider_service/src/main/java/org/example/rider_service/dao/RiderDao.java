package org.example.rider_service.dao;

import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.example.rider_service.dto.RiderDto;
import org.example.rider_service.entity.Rider;
import org.example.rider_service.entity.Status;
import org.example.rider_service.repository.RiderRepository;
import org.locationtech.jts.geom.Point;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.nio.file.FileAlreadyExistsException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@Slf4j
@Builder
public class RiderDao {

    @Autowired
    private RiderRepository riderRepository;


    public void riderCreate(RiderDto riderDto)throws Exception{

        if(riderCheck(riderDto.getRiderId()).isEmpty()) {

            Rider rider = Rider.riderBuilderFromDto(riderDto);
            rider.setNextOrder(LocalDateTime.now());
            riderRepository.save(rider);

        }else throw new FileAlreadyExistsException("User Exists");

    }


    public void updateRiderStatus(Rider rider, Status status){
        rider.setRiderStatus(status);
        riderRepository.save(rider);
    }
    public void updateNextOrderBreak(Rider rider, LocalDateTime nextOrderTime){
        rider.setNextOrder(nextOrderTime);
        riderRepository.save(rider);
    }


    public Optional<Rider> riderCheck(UUID riderId){
        return riderRepository.getRiderById(riderId);
    }

    public void saveRider(Rider rider){
        riderRepository.save(rider);
    }


    public Rider findRiderByPoint(Point point, double radius){
        return riderRepository.findNearestRiderInGeoFixedRadius(point,radius);
    }


}
