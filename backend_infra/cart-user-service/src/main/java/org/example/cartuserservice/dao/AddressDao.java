package org.example.cartuserservice.dao;

import lombok.extern.slf4j.Slf4j;
import org.example.cartuserservice.entity.Address;
import org.example.cartuserservice.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@Slf4j
public class AddressDao {


    @Autowired
    private AddressRepository addressRepository;


    public void saveAddress(Address address){
        addressRepository.save(address);
    }

    public List<Address> getAllAddressForUser(UUID userId){

        return addressRepository.findListAddressByUserId(userId);
    }

    public Address getAddressByAddressId(long addressId){
        return addressRepository.findAddressByAddressId(addressId);
    }


}
