package org.example.rider_service.service;

import lombok.extern.slf4j.Slf4j;
import org.example.rider_service.dao.RiderDao;
import org.example.rider_service.dto.RiderDto;
import org.example.rider_service.entity.Rider;
import org.example.rider_service.entity.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Slf4j
public class RiderService {

    @Autowired
    private RiderDao riderDao;


    public void saveRiderDb(RiderDto riderDto){
        try {
            riderDao.riderCreate(riderDto);
        }catch (Exception e){
            log.error(e.getLocalizedMessage());
        }
    }

    @Transactional
    public void updateRiderStatus(Rider rider, Status status){
        riderDao.updateRiderStatus(rider,status);
    }


    public void updateRiderNextOrder(Rider rider, LocalDateTime localDateTime){
        riderDao.updateNextOrderBreak(rider,localDateTime);
    }


}
