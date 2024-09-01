package org.example.cartuserservice.service;


import lombok.extern.slf4j.Slf4j;
import org.example.cartuserservice.dao.AddressDao;
import org.example.cartuserservice.dao.UserDao;
import org.example.cartuserservice.dto.AddressDto;
import org.example.cartuserservice.dto.AddressResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
public class UserPrService {


    @Autowired
    private UserUtilityService userUtilityService;



    @Async
    public void userAddress(AddressDto addressDto, UUID userId){
        // check user if not then create

        // address create

        //rest template to locate nearby store

        userUtilityService.setUserAddress(addressDto,userId);
    }


    @Async
    public CompletableFuture<List<AddressResponseDto>> responseDtoListQuery(UUID userId) {
        return CompletableFuture.supplyAsync(() -> userUtilityService.getAllAddressByUser(userId));
    }



}
