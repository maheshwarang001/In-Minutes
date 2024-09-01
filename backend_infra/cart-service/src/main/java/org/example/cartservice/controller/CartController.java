package org.example.cartservice.controller;


import lombok.extern.slf4j.Slf4j;
import org.example.cartservice.dto.AddressDto;
import org.example.cartservice.dto.AddressResponseDto;
import org.example.cartservice.dto.UserCartDto;
import org.example.cartservice.entity.Address;
import org.example.cartservice.entity.Cart;
import org.example.cartservice.service.AddressService;
import org.example.cartservice.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.InvalidParameterException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/cart/v1")
@Slf4j
public class CartController {


    @Autowired
    private AddressService addressService;

    @Autowired
    private CartService cartService;


//    @GetMapping("/add-cart")
//    public ResponseEntity<String> addCart(UserCartDto userCartDto) {
//        try {
//
//            if(userCartDto.getQty() <= 0 || userCartDto.getQty() > 20) throw new InvalidParameterException();
//
//            cartService.createOrUpdateCart(userCartDto);
//            return ResponseEntity.ok("Cart updated successfully");
//
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update cart: " + e.getMessage());
//        }
//    }

    @GetMapping("/delete-cart")
    public ResponseEntity<String> deleteCart(@RequestParam UUID cartId){
        try {

            cartService.cartCompleted(cartId);
            return ResponseEntity.ok("Cart updated successfully");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update cart: " + e.getMessage());
        }
    }


    @PostMapping("/cart/estimate")
    public void cartEstimate(@RequestParam UUID uid, @RequestParam UUID addressId){

        cartService.getEstimate(uid);

    }


    @PostMapping("/confirm/order")
    public ResponseEntity<Cart> placeOrder(@RequestParam UUID userId){

        try {

            CompletableFuture<Optional<Cart>> cart = cartService.getCart(userId);

            if(cart.get().isEmpty()) throw new ClassNotFoundException();

            cartService.kafkaConfirmOrder(cart.get().get());

            return ResponseEntity.status(HttpStatus.OK).body(cart.get().get());

        }catch (Exception e){
            log.error(e.getLocalizedMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }

    }


    //increment endpoint

    @PostMapping("/increment")
    public ResponseEntity<Void> incrementProductCart(@RequestParam UUID productId, @RequestParam UUID userId){

        return ResponseEntity.ok().body(null);
    }

    @PostMapping("/decrement")
    public ResponseEntity<Void> decrementProductCart(@RequestParam UUID productId, @RequestParam UUID userId){
        return ResponseEntity.ok().body(null);
    }


    @PostMapping("/set/address")
    public ResponseEntity<Void> setAddress(@RequestBody AddressDto addressDto , @RequestParam UUID userId){
        try {
            addressService.saveAddress(addressDto,userId);
            return ResponseEntity.ok().body(null);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping()
    public ResponseEntity<List<AddressResponseDto>> getAddressForUser(@RequestParam UUID userId) {
        try {
            CompletableFuture<List<AddressResponseDto>> responseDtoCompletableFuture = addressService.getAddressForTheUser(userId);
            List<AddressResponseDto> addressResponseDtos = responseDtoCompletableFuture.get();

            return ResponseEntity.ok(addressResponseDtos);
        } catch (Exception e) {

            return ResponseEntity.badRequest().body(null);
        }
    }






}
