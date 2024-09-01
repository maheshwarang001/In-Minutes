package org.example.cartservice.dao;

import lombok.extern.slf4j.Slf4j;
import org.example.cartservice.entity.Address;
import org.example.cartservice.repository.AddressRepository;
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

    public List<Address> findAllAddress(UUID userId){
        return addressRepository.getAllAddressByUserId(userId);
    }

    public Address findAddressById(UUID addressId){
        return addressRepository.findAddressById(addressId);
    }
}
