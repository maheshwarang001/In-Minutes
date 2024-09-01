package org.example.cartservice.service;

import lombok.extern.slf4j.Slf4j;
import org.example.cartservice.Producer.KafkaMessageProducer;
import org.example.cartservice.configuration.Topics;
import org.example.cartservice.dao.CartDao;
import org.example.cartservice.dto.*;
import org.example.cartservice.entity.Cart;
import org.example.cartservice.entity.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.swing.text.html.Option;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.example.cartservice.Producer.KafkaMessageProducer.serialize;

@Service
@Slf4j
public class CartService {

    @Autowired
    private CartUtilityService cartUtilityService;

    @Autowired
    private KafkaMessageProducer kafkaMessageProducer;


    @Autowired
    private CartDao cartDao;

    //check if cart is open or add Or Update Cart Also Check For The Store, if store different ask to delete the cart;



    @Async
    public void incrementProduct(UUID product, UUID user){

    }

    @Async
    public void createOrUpdateCart(UserCartDto userCartDto) throws ExecutionException, InterruptedException {
        //cartUtilityService.updateCart(userCartDto);
    }

    //Queue notifies if the cart has been checkout then mark it paid;

    //delete cart
    @Async
    public void cartCompleted(UUID cartId){
        try {
            cartUtilityService.deleteCart(cartId);
        }catch (Exception e){
            log.error(e.getLocalizedMessage());
        }
    }

    public CompletableFuture<Optional<Cart>> getCart(UUID userId){

        return cartDao.findCartByUserId(userId);
    }

    public void kafkaConfirmOrder(Cart cart){

        try {

            Event event = Event
                    .builder()
                    .entity("order")
                    .object(serialize(cart))
                    .timeStamp(LocalDateTime.now())
                    .build();

            kafkaMessageProducer.sendMessage(event, Topics.CART_TOPIC);

        }catch (Exception e){
            log.error(e.getLocalizedMessage());
            throw e;
        }
    }


//    public CartOrderResponseDto getEstimate(UUID userId) {
//        try {
//            CartOrderCheckList checkList = prepareCartCheck(userId);
//            RestTemplate restTemplate = new RestTemplate();
//
//            ResponseEntity<CartOrderResponseDto> responseEntity = restTemplate.postForEntity(
//                    "http://localhost:9092/inventory-read/check/v1/cart/final",
//                    checkList,
//                    CartOrderResponseDto.class
//            );
//
//            if (responseEntity.getStatusCode().is2xxSuccessful()) {
//                return responseEntity.getBody();
//            } else {
//                throw new RuntimeException("Failed to get cart estimate. HTTP Status: " + responseEntity.getStatusCode());
//            }
//        } catch (Exception e) {
//            System.err.println("Error retrieving cart estimate: " + e.getMessage());
//            throw new RuntimeException("Error retrieving cart estimate", e);
//        }
//    }




//    private CartOrderCheckList prepareCartCheck(UUID userId) throws Exception {
//
//        try{
//
//            CompletableFuture<Optional<Cart>> cart = getCart(userId);
//
//            if(cart.get().isEmpty()) throw new Exception();
//
//            List<CartOrderItemDto> orderItemDtoList = new ArrayList<>();
//
//            for(Item item : cart.get().get().getItemList()){
//
//                CartOrderItemDto cartOrderItemDto = CartOrderItemDto
//                        .builder()
//                        .productId(item.getProduct().getProductId())
//                        .qty(item.getQty())
//                        .build();
//                orderItemDtoList.add(cartOrderItemDto);
//            }
//            return  CartOrderCheckList
//                    .builder()
//                    .storeId(cart.get().get().getCartId())
//                    .cartOrderItemDtoList(orderItemDtoList)
//                    .build();
//
//
//        }catch (Exception e){
//            log.error(e.getLocalizedMessage());
//            throw e;
//        }
//
//    }


}
