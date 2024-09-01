package org.example.order_service.repository;

import org.example.order_service.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order,Long> {


    @Query("SELECT os FROM Order os WHERE os.orderId = :orderId")
    Optional<Order> findOrderByOrderId(@Param("orderId")Long orderId);






}
