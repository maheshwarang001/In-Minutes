package org.example.cartuserservice.service;


import lombok.extern.slf4j.Slf4j;
import org.example.cartuserservice.dao.AddressDao;
import org.example.cartuserservice.dao.UserDao;
import org.example.cartuserservice.dto.AddressDto;
import org.example.cartuserservice.dto.AddressResponseDto;
import org.example.cartuserservice.entity.Address;
import org.example.cartuserservice.entity.User;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Component
@Slf4j
public class UserUtilityService {


    @Autowired
    private UserDao userDao;

    @Autowired
    private AddressDao addressDao;

    @Transactional
    public void setUserAddress(AddressDto addressDto, UUID userId){


        User existingUser = userDao.findUserById(userId);

        if(existingUser == null){
            User newUser = User.builder().userId(userId).build();
            userDao.saveUser(newUser);
        }

        Coordinate coordinate = new Coordinate(addressDto.getLat(),addressDto.getLog());
        GeometryFactory geometryFactory = new GeometryFactory();
        Point customerPoint = geometryFactory.createPoint(coordinate);

        Address address = Address
                .builder()
                .address(addressDto.getAddress())
                .city(addressDto.getCity())
                .postcode(addressDto.getPostcode())
                .user(existingUser)
                .customerLocation(customerPoint)
                .build();

        addressDao.saveAddress(address);

    }

    public List<AddressResponseDto> getAllAddressByUser(UUID userId){

            List<Address> addressList = addressDao.getAllAddressForUser(userId);

            return addressList.stream()
                    .map(it -> AddressResponseDto.builder()
                            .addressId(it.getAddressId())
                            .address(it.getAddress())
                            .city(it.getCity())
                            .postcode(it.getPostcode())
                            .build())
                    .collect(Collectors.toList());


    }
}
