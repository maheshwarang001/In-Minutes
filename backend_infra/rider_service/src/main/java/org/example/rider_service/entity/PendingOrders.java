//package org.example.rider_service.entity;
//
//import jakarta.persistence.*;
//import lombok.AllArgsConstructor;
//import lombok.Builder;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//import java.time.LocalDateTime;
//import java.util.UUID;
//
//@Data
//@Entity
//@Builder
//@AllArgsConstructor
//@NoArgsConstructor
//public class PendingOrders {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    private UUID orderId;
//
//    @OneToOne
//    @JoinColumn(name = "order_id", referencedColumnName = "orderId")
//    private Order order;
//
//    private LocalDateTime lastTryAt;
//
//    private int retryCount;
//
//    @Version
//    private long version;
//}
