package org.example.rider_service.repository;

import org.example.rider_service.entity.OrderStatus;
import org.example.rider_service.entity.PendingOrders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PendingOrderRepository extends JpaRepository<PendingOrders, UUID> {


    @Query("SELECT ps FROM PendingOrders ps WHERE ps.orderStatus = :orderStatus")
    List<PendingOrders> findPendingOrdersByOrderStatus(@Param("orderStatus")OrderStatus orderStatus);


    @Query("SELECT ps FROM PendingOrders ps WHERE  ps.orderId = :orderId")
    Optional<PendingOrders> findOrderFromPending(@Param("orderId") UUID orderId);



}
