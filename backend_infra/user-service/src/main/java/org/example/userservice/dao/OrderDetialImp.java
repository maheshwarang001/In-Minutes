package org.example.userservice.dao;

import org.example.userservice.entity.order.OrderStatus;
import org.example.userservice.entity.order.PaymentStatus;
import org.example.userservice.model.OrderDetailPatch;

import java.util.UUID;

public interface OrderDetialImp {

    void createOrder(OrderDetailPatch orderDetailPatch) throws Exception ;

    void updateOrderStatus(OrderStatus orderStatus, UUID orderId) throws Exception ;

    void updateOrderPaymentStatus(PaymentStatus paymentStatus, UUID orderId) throws Exception ;

}
