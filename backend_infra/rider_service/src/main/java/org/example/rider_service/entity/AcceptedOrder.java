//package org.example.rider_service.entity;
//
//import jakarta.persistence.*;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//import java.time.LocalDateTime;
//import java.util.UUID;
//
//@Entity
//@Data
//@AllArgsConstructor
//@NoArgsConstructor
//public class AcceptedOrder {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    private UUID acceptedOrderId;
//
//    @OneToOne
//    @JoinColumn(name = "order_id", referencedColumnName = "orderId")
//    private Order order;
//
//    @OneToOne
//    @JoinColumn(name = "rider_id", referencedColumnName = "riderId")
//    private Rider rider;
//
//    private LocalDateTime acceptedAt;
//
//    @Version
//    private long version;
//}
