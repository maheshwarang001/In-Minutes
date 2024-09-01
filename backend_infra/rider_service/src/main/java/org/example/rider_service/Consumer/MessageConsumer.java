package org.example.rider_service.Consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.example.rider_service.dto.Event;
import org.example.rider_service.dto.OrderDto;
import org.example.rider_service.dto.RiderLocationDto;
import org.example.rider_service.dto.StatusChange;
import org.example.rider_service.service.OrderService;
import org.example.rider_service.service.RiderLocationExpoService;
import org.example.rider_service.service.RiderLocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Slf4j
public class MessageConsumer {

    @Autowired
    private OrderService orderService;

    @Autowired
    private RiderLocationExpoService riderLocationExpoService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    public void aggregate(Event event) {

        try {
            if(event.getName().isBlank())throw new NullPointerException();
            switch (event.getName()) {

                case "order" -> {
                    OrderDto orderDto = deserialize(event.getMessage(), OrderDto.class);
                    if (orderDto != null) {
                        orderService.receiveOrder(orderDto);
                    } else {
                        log.warn("Received null OrderDto from event: {}", event);
                    }
                }

                case "rider-location-update", "rider-location-close", "rider-location-network" -> {
                    if (event.getName().contains("update")) {
                        RiderLocationDto riderLocationDto = deserialize(event.getMessage(), RiderLocationDto.class);
                        if (riderLocationDto != null) {
                            riderLocationExpoService.updateRiderLocation(riderLocationDto);
                        } else {
                            log.warn("Received null RiderLocationDto from event: {}", event);
                        }
                    } else {
                        StatusChange statusChange = deserialize(event.getMessage(), StatusChange.class);
                        if (statusChange != null) {
                            riderLocationExpoService.riderStatus(statusChange);
                        } else {
                            log.warn("Received null StatusChange from event: {}", event);
                        }
                    }
                }

                default -> log.warn("Unknown event type received: {}", event);
            }

        } catch (Exception e) {
            log.error("Error processing event: {}", event, e);
        }

    }

    private <T> T deserialize(byte[] bytes, Class<T> tClass) {
        try {
            return objectMapper.readValue(bytes, tClass);
        } catch (IOException e) {
            log.error("Json processing failed for object: {}", new String(bytes), e);
            return null;
        }
    }
}
