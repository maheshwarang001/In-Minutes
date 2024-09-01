package org.example.cartuserservice.controller;

import jakarta.ws.rs.POST;
import lombok.extern.slf4j.Slf4j;
import org.example.cartuserservice.dto.*;
import org.example.cartuserservice.service.CartService;
import org.example.cartuserservice.service.UserPrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Slf4j
@RestController
@RequestMapping("/cart-service/v1")
public class MainController {

    @Autowired
    private UserPrService userPrService;

    @Autowired
    private CartService cartService;

    @GetMapping("/user/address")
    public ResponseEntity<List<AddressResponseDto>> getAddressesForUser(@RequestParam UUID userId) {
        CompletableFuture<List<AddressResponseDto>> futureResponse = userPrService.responseDtoListQuery(userId);

        try {
            List<AddressResponseDto> addressResponseDtos = futureResponse.get();
            return ResponseEntity.ok(addressResponseDtos);
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    @PostMapping("/user/create/address")
    public ResponseEntity<Void> createAddressAndUser(@RequestParam AddressDto addressDto , @RequestParam UUID userId){
        userPrService.userAddress(addressDto, userId);
        return ResponseEntity.ok().body(null);
    }




    @GetMapping("/increment")
    public ResponseEntity<Void> increment(@RequestParam UUID userId, @RequestParam UUID productId, @RequestParam UUID storeId) {
        try {
            cartService.incrementProductIntoCart(userId, productId, storeId).get();
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            log.error(e.getLocalizedMessage(), e);
            return ResponseEntity.status(500).build();
        }
    }

    @GetMapping("/decrement")
    public ResponseEntity<Void> decrement(@RequestParam UUID userId, @RequestParam UUID productId) {
        try {
            cartService.decrement(productId, userId).get();
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            log.error(e.getLocalizedMessage(), e);
            return ResponseEntity.status(500).build();
        }
    }

    @GetMapping("/estimate")
    public ResponseEntity<EstimateDto> estimateCheckout(@RequestParam UUID userId, @RequestParam long addressId){

        try {

            EstimateDto estimate = cartService.getEstimate(userId,addressId).get();
            cartService.map.put(userId,estimate);
            return ResponseEntity.ok().body(estimate);

        }catch (Exception e){
            log.error(e.getLocalizedMessage());
            return ResponseEntity.badRequest().build();
        }

    }


    @PostMapping("/checkout-cart")
    public ResponseEntity<CreatePaymentResponse> createPaymentResponseIntent( @RequestParam UUID userId, long addressId ){
        try {
            OrderQueryDto orderQueryDto = cartService.getOrderQuery(userId, addressId).get();
            CreatePaymentResponse createPaymentResponse = cartService.getPaymentIntentFromOrderService(orderQueryDto).get();
            return ResponseEntity.ok().body(createPaymentResponse);

        }catch (Exception e){
            log.error(e.getLocalizedMessage());
            return ResponseEntity.badRequest().build();
        }
    }



}
