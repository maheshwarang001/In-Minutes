package org.example.rider_service.service;


import lombok.extern.slf4j.Slf4j;
import org.example.rider_service.Producer.KafkaMessageProducer;
import org.example.rider_service.configuration.Topics;
import org.example.rider_service.dto.Event;
import org.example.rider_service.dto.OrderDto;
import org.example.rider_service.dto.OrderRiderDto;
import org.example.rider_service.entity.Rider;
import org.example.rider_service.entity.Status;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.concurrent.CompletableFuture;

import static org.example.rider_service.Producer.KafkaMessageProducer.serialize;

@Service
@Slf4j
public class OrderService {

    @Autowired
    private RiderService riderService;

    @Autowired
    private RiderLocationService riderLocationService;

    @Autowired
    private KafkaMessageProducer kafkaMessageProducer;


    protected void getOrderAndPassItToRiders(OrderRiderDto orderRiderDto){

        try {

            CompletableFuture<Rider> availableRiders = riderLocationService.findRiderInFixedRadius(orderRiderDto.getStoreLocation(), 4.0);

            Rider ridersInRadius = availableRiders.join();

            if(ridersInRadius != null){

                orderRiderDto.setRiderId(ridersInRadius.getRiderId());

                Event eventOrder = Event
                        .builder()
                        .name("rider-order-broadcast")
                        .message(serialize(orderRiderDto))
                        .build();

                kafkaMessageProducer.sendMessage(eventOrder, Topics.LOCATION_TOPIC);

                riderService.updateRiderNextOrder(ridersInRadius, LocalDateTime.now().plusSeconds(50));

            }else{

                log.info("NO FREE RIDER IN THE RADIUS");

            }

        }catch (Exception e){

            log.error(e.getLocalizedMessage());

        }

    }

    @Async
    public void receiveOrder(OrderDto orderDto) {

        OrderRiderDto orderRiderDto = OrderRiderDto
                .builder()
                .orderId(orderDto.getOrderId())
                .storeLocation(orderDto.getStoreLocation())
                .deliveryCompensation(orderDto.getDeliveryCompensation())
                .customerTip(orderDto.getCustomerTip())
                .handlingCost(orderDto.getHandlingCost())
                .avgTotalDistance(orderDto.getAvgTotalDistance())
                .bonus(orderDto.getBonus())
                .build();


        getOrderAndPassItToRiders(orderRiderDto);
    }
}
