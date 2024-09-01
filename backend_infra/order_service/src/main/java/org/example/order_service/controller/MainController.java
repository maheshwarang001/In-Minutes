package org.example.order_service.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.order_service.dto.CreatePaymentResponse;
import org.example.order_service.dto.OrderQueryDto;
import org.example.order_service.service.OrderService;
import org.example.order_service.service.ServicePayment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@Slf4j
@RequestMapping("/order-service/v1")
public class MainController {


    private OrderService orderService;
    private ServicePayment servicePayment;

    private RestTemplate template;


    @PostMapping("/create-order")
    public ResponseEntity<CreatePaymentResponse> receiveOrder(@RequestBody OrderQueryDto orderQueryDto){
        try {
            CreatePaymentResponse response =  orderService.createOrder(orderQueryDto);
            return ResponseEntity.ok().body(response);
        }catch (Exception e){
            log.error(e.getLocalizedMessage());
            return ResponseEntity.badRequest().build();
        }
    }
}
