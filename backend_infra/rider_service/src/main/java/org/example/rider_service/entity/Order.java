//package org.example.rider_service.entity;
//
//import jakarta.persistence.*;
//import lombok.*;
//
//import java.time.LocalDateTime;
//import java.util.UUID;
//
//
//@Data
//@Entity
//@Builder
//@AllArgsConstructor
//@NoArgsConstructor
//public class Order {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    private UUID orderId;
//
//    @OneToOne
//    @JoinColumn(name = "benefit_id")
//    private Benefit benefit;
//
//    private double avgTotalDistance;
//
//    private LocalDateTime createTimeStamp;
//
//    @Enumerated(EnumType.STRING)
//    private OrderStatus orderStatus;
//
//    @Version
//    private long version;
//}