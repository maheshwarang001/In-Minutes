package org.example.userservice.repository;

import org.example.userservice.entity.order.OrderDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetails, UUID> {
    //createorder
    //updatorderstatus

    OrderDetails findByOrderId(UUID uuid);


}
