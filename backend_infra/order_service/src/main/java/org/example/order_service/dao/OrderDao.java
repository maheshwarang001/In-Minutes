package org.example.order_service.dao;

import lombok.extern.slf4j.Slf4j;
import org.example.order_service.entity.Order;
import org.example.order_service.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.text.html.Option;

@Component
@Slf4j
public class OrderDao {

    @Autowired
    private OrderRepository orderRepository;


    public void saveOrder(Order order){
        orderRepository.save(order);
    }


    public Optional<Order> getOrderByCartId(){

    }
}
