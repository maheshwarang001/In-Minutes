package org.example.order_service.service;

import lombok.extern.slf4j.Slf4j;
import org.example.order_service.dto.*;
import org.example.order_service.entity.Order;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Service
@Slf4j
public class OrderService {



    //method to get darkstore

    @Autowired
    private ServicePayment servicePayment;

    @Autowired
    private OrderUtilService orderUtilService;

    private RestTemplate restTemplate = new RestTemplate();

    private DarkStoreResponseDto getDarkStoreInfoFromInventoryRead(UUID darkStoreId, List<ItemDetailsDto> items) throws Exception {
        try {
            ResponseEntity<DarkStoreResponseDto> response = restTemplate.exchange(
                    "your-inventory-service-url", // Replace with the actual URL
                    HttpMethod.POST,
                    new HttpEntity<>(DarKStoreQuery.builder()
                            .darkStoreId(darkStoreId)
                            .items(items)
                            .build()),
                    DarkStoreResponseDto.class
            );

            if (response.getStatusCode() == HttpStatus.OK) {
                return response.getBody();
            } else {
                log.error("Failed to retrieve dark store info: HTTP status code {}", response.getStatusCode());
                throw new Exception("Failed to retrieve dark store info. HTTP status code: " + response.getStatusCode());
            }

        } catch (Exception e) {
            log.error("Error occurred while retrieving dark store info: {}", e.getMessage());
            throw e;
        }
    }

    //method to get customer details

    private UserResponseDto getUserInfoFromUserService(UUID userId) throws Exception {
        try {
            ResponseEntity<UserResponseDto> response = restTemplate.exchange(
                    "your-user-service-url", // Replace with the actual URL
                    HttpMethod.POST,
                    new HttpEntity<>(userId),
                    UserResponseDto.class
            );

            if (response.getStatusCode() == HttpStatus.OK) {
                return response.getBody();
            } else {
                log.error("Failed to retrieve user info: HTTP status code {}", response.getStatusCode());
                throw new Exception("Failed to retrieve user info. HTTP status code: " + response.getStatusCode());
            }

        } catch (Exception e) {
            log.error("Error occurred while retrieving user info: {}", e.getMessage());
            throw e;
        }
    }


    @Transactional
    public CreatePaymentResponse createOrder(OrderQueryDto orderQueryDto){


        //locate the nearest store and reserve the products

        CompletableFuture<DarkStoreResponseDto> darkStoreResponseDtoCompletableFuture = CompletableFuture.supplyAsync(()-> {
            try {
                return getDarkStoreInfoFromInventoryRead(orderQueryDto.getAddressOrder().getNearestStore(), orderQueryDto.getProductDetailDtoList());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        CompletableFuture<UserResponseDto> userResponseCompletableFuture = CompletableFuture.supplyAsync(()->{
         try{

             return getUserInfoFromUserService(orderQueryDto.getUserId());

         }catch (Exception e){
             throw new RuntimeException();
         }
       });

        try {

            CompletableFuture<Void> allOfFuture = CompletableFuture.allOf(darkStoreResponseDtoCompletableFuture, userResponseCompletableFuture);

            allOfFuture.join();

            DarkStoreResponseDto darkStoreResponse = darkStoreResponseDtoCompletableFuture.get();
            UserResponseDto userResponseDto = userResponseCompletableFuture.get();


            //order-created

            Order order = orderUtilService.createOrder(
                    OrderInit.builder()
                            .referenceId(orderQueryDto.getCartId())
                            .customerId(orderQueryDto.getUserId())
                            .darkStoreId(darkStoreResponse.getStoreId())
                            .invoice(orderQueryDto.getInvoiceDto())
                            .items(orderQueryDto.getProductDetailDtoList())
                            .build());

            //request stripe




            return  servicePayment.callBackPaymentService(
                    CartOrderRequest
                            .builder()
                            .orderId(order.getOrderId())
                            .userId(order.getCustomerId())
                            .firstName(userResponseDto.getFirstName())
                            .lastName(userResponseDto.getLastName())
                            .address(
                                    Address
                                            .builder()
                                            .address(orderQueryDto.getAddressOrder().getAddress())
                                            .city(orderQueryDto.getAddressOrder().getCity())
                                            .postcode(orderQueryDto.getAddressOrder().getPostcode())
                                            .lat(orderQueryDto.getAddressOrder().getCustomerLocation().getX())
                                            .log(orderQueryDto.getAddressOrder().getCustomerLocation().getY())
                                            .build()
                            )
                            .referenceNumber(order.getReferenceNo())
                            .payable(order.getInvoice().getPayable())
                            .build()
            ).get();


        } catch (InterruptedException | ExecutionException e) {
            // Log and rethrow the exception if needed
            log.error("Error creating payment response", e);
            throw new RuntimeException("Error creating payment response", e);
        }


    }







    //method to return customer details and order ID
}
