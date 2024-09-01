package org.example.userservice.service;

import lombok.extern.slf4j.Slf4j;
import org.example.userservice.dao.OrderDetailDao;
import org.example.userservice.entity.order.OrderStatus;
import org.example.userservice.entity.order.PaymentStatus;
import org.example.userservice.model.OrderDetailPatch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@Service
public class OrderService {

    @Autowired
    OrderDetailDao orderDetailDao;

    @Transactional
    public void placeOrder(OrderDetailPatch orderDetailPatch) throws Exception {
        try {
            orderDetailDao.createOrder(orderDetailPatch);
        }catch (Exception e){
            throw  e;
        }
    }

    @Transactional
    public void updateOrderStatus(OrderStatus orderStatus, UUID orderId)throws Exception{
        try {
            orderDetailDao.updateOrderStatus(orderStatus,orderId);
        }catch (Exception e){
            throw  e;
        }
    }

    @Transactional
    public void updatePaymentStatus(PaymentStatus paymentStatus, UUID orderId)throws Exception{
        try {
            orderDetailDao.updateOrderPaymentStatus(paymentStatus,orderId);
        }catch (Exception e){
            throw  e;
        }
    }




}
