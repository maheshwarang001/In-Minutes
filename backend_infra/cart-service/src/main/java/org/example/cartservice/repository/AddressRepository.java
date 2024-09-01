package org.example.cartservice.repository;

import org.example.cartservice.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {


    @Query("SELECT ps FROM Address ps WHERE ps.user.userId = :userId")
    List<Address>getAllAddressByUserId(@Param("userId")UUID userId);

    @Query("SELECT ps FROM Address  ps where ps.addressId = :addressId")
    Address findAddressById(@Param("addressId") UUID addressId);


}
