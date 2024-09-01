package org.example.order_service.service;

import org.example.order_service.dto.CartOrderRequest;
import org.example.order_service.dto.CreatePaymentResponse;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.CompletableFuture;

@Service
public class ServicePayment {


    private RestTemplate restTemplate = new RestTemplate();

    @Async
    public CompletableFuture<CreatePaymentResponse> callBackPaymentService(CartOrderRequest cartOrderRequest){

        return CompletableFuture.supplyAsync(()-> {
            try {
                ResponseEntity<CreatePaymentResponse> response = restTemplate.exchange("", HttpMethod.POST, new HttpEntity<>(cartOrderRequest), CreatePaymentResponse.class);

                if (response.getStatusCode() == HttpStatus.OK) {
                    return response.getBody();
                } else {
                    throw new RuntimeException("Failed to retrieve payment info. HTTP status code: " + response.getStatusCode());
                }
            } catch (Exception e) {
                throw e;
            }
        });
    }


}
