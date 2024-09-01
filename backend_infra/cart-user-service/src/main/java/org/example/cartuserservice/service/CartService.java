package org.example.cartuserservice.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.shaded.io.opentelemetry.proto.trace.v1.Status;
import org.example.cartuserservice.dto.CreatePaymentResponse;
import org.example.cartuserservice.dto.EstimateDto;
import org.example.cartuserservice.dto.OrderQueryDto;
import org.example.cartuserservice.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Service
@Slf4j
public class CartService {


    @Autowired
    private CartUtils cartUtils;

    public Map<UUID,EstimateDto> map = new HashMap<>();


    private RestTemplate restTemplate = new RestTemplate();


    @Async
    public CompletableFuture<Void> decrement(UUID productId, UUID userId) {
        return CompletableFuture.runAsync(() -> cartUtils.decrementProductCart(productId, userId));
    }

    @Async
    public CompletableFuture<Void> incrementProductIntoCart(UUID userId, UUID productId, UUID storeId) {
        return CompletableFuture.runAsync(() -> cartUtils.incrementProductInCart(productId, userId, storeId));
    }


    @Async
    public CompletableFuture<EstimateDto> getEstimate(UUID userId, long addressId) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                map.remove(userId);
                return cartUtils.provideEstimateDto(userId, addressId);
            } catch (Exception e) {

                log.error("Error while providing estimate: {}", e.getMessage());
                throw new RuntimeException("Failed to get estimate", e);
            }
        });
    }

    @Async
    public CompletableFuture<OrderQueryDto> getOrderQuery(UUID userId, long addressId) {
        return CompletableFuture.supplyAsync(() -> {
            try {

                EstimateDto estimateDto;

                if (map.get(userId) == null) {
                    log.info("Estimate for user {} not found in cache, fetching...", userId);
                    try {
                        estimateDto = getEstimate(userId, addressId).get();
                    } catch (InterruptedException | ExecutionException e) {
                        log.error("Error fetching estimate: {}", e.getMessage());
                        throw new RuntimeException("Error fetching estimate", e);
                    }
                } else {
                    estimateDto = map.get(userId);
                }

                return OrderQueryDto.builder()
                        .cartId(estimateDto.getCartId())
                        .userId(userId)
                        .addressOrder(estimateDto.getAddressId())
                        .productDetailDtoList(estimateDto.getProductDetailDtoList())
                        .invoiceDto(estimateDto.getInvoiceDto())
                        .build();
            } catch (Exception e) {
                log.error("Error processing order query: {}", e.getMessage());
                throw new RuntimeException("Error processing order query", e);
            }
        });
    }


    @Async
    public CompletableFuture<CreatePaymentResponse> getPaymentIntentFromOrderService(OrderQueryDto orderQueryDto) {
        return CompletableFuture.supplyAsync(() -> {
            ResponseEntity<CreatePaymentResponse> responseEntity =
                    restTemplate.exchange("your-order-service-url", HttpMethod.POST, new HttpEntity<>(orderQueryDto), CreatePaymentResponse.class);

            if (responseEntity.getStatusCode() == HttpStatus.OK) {
                return responseEntity.getBody();
            } else {
                throw new RuntimeException("Failed to get payment intent: " + responseEntity.getStatusCode());
            }
        });
    }



}
