package org.example.cartservice.service;

import lombok.extern.slf4j.Slf4j;
import org.example.cartservice.dao.AddressDao;
import org.example.cartservice.dao.UserDao;
import org.example.cartservice.dto.AddressDto;
import org.example.cartservice.dto.AddressResponseDto;
import org.example.cartservice.entity.Address;
import org.example.cartservice.entity.User;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

@Service
@Slf4j
public class AddressService {


    @Autowired
    private AddressDao addressDao;

    @Autowired
    private UserDao userDao;


    @Async
    public CompletableFuture<List<AddressResponseDto>> getAddressForTheUser(UUID userId) {
        return CompletableFuture.supplyAsync(() -> addressDao.findAllAddress(userId))
                .thenApply(addressList ->
                        addressList.stream()
                                .map(it -> AddressResponseDto.builder()
                                        .addressId(it.getAddressId())
                                        .address(it.getAddress())
                                        .city(it.getCity())
                                        .postcode(it.getPostcode())
                                        .lat(it.getPoint().getX())
                                        .log(it.getPoint().getY())
                                        .build())
                                .collect(Collectors.toList())
                );
    }


    @Async
    public void saveAddress(AddressDto addressDto , UUID userId){

        User user = userDao.findUserById(userId);

        if(user == null) throw new InvalidParameterException();

        Coordinate coordinate = new Coordinate(addressDto.getLat(), addressDto.getLog());
        Point addressPoint = new GeometryFactory().createPoint(coordinate);

        addressDao.saveAddress(
                Address
                        .builder()
                        .address(addressDto.getAddress())
                        .postcode(addressDto.getPostcode())
                        .city(addressDto.getCity())
                        .point(addressPoint)
                        .user(user)
                        .build());
    }


}
