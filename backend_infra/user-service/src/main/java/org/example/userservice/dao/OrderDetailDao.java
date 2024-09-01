package org.example.userservice.dao;

import org.example.userservice.entity.order.OrderDetails;
import org.example.userservice.entity.order.OrderStatus;
import org.example.userservice.entity.order.PaymentStatus;
import org.example.userservice.model.OrderDetailPatch;
import org.example.userservice.repository.OrderDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class OrderDetailDao implements OrderDetialImp{

    @Autowired
    OrderDetailRepository orderDetailRepository;


    @Override
    public void createOrder(OrderDetailPatch orderDetailPatch) throws Exception {

            OrderDetails orderDetails = new OrderDetails();
            orderDetails.setOrderId(orderDetailPatch.getOrderID());
            orderDetails.setItems(orderDetailPatch.getItems());
            orderDetails.setStoreId(orderDetailPatch.getStoreId());
            orderDetails.setAddress(orderDetailPatch.getAddress());
            orderDetails.setUserDetail(orderDetailPatch.getUserDetail());
            //orderDetails.setItemTotal(orderDetailPatch.);
            orderDetails.setOrderStatus(OrderStatus.PROCESSING);
            orderDetails.setPaymentStatus(PaymentStatus.INCOMPLETE);


    }

    @Override
    public void updateOrderStatus(OrderStatus orderStatus, UUID orderId) throws Exception{

        OrderDetails orderDetails = orderDetailRepository.findByOrderId(orderId);
        if(orderDetails == null) throw new NullPointerException();

        try{
            orderDetails.setOrderStatus(orderStatus);
        }catch (Exception e){
            throw e;
        }

    }

    @Override
    public void updateOrderPaymentStatus(PaymentStatus paymentStatus,UUID orderId) {
        OrderDetails orderDetails = orderDetailRepository.findByOrderId(orderId);
        if(orderDetails == null) throw new NullPointerException();

        try{
            orderDetails.setPaymentStatus(paymentStatus);
        }catch (Exception e){
            throw e;
        }

    }
}
