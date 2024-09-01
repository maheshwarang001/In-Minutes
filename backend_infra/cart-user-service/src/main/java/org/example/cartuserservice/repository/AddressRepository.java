package org.example.cartuserservice.repository;

import org.example.cartuserservice.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface AddressRepository extends JpaRepository<Address, UUID> {


    @Query("SELECT ps FROM Address ps WHERE ps.user.userId = :userId")
    List<Address> findListAddressByUserId(@Param("userId") UUID userId);


    @Query("SELECT ps FROM Address ps WHERE ps.addressId = :addressId")
    Address findAddressByAddressId(@Param("addressId") long addressId);
}
