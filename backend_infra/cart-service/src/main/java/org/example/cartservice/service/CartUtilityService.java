package org.example.cartservice.service;

import org.apache.kafka.common.errors.InvalidRequestException;
import org.example.cartservice.dao.CartDao;
import org.example.cartservice.dao.UserDao;
import org.example.cartservice.dto.UserCartDto;
import org.example.cartservice.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Component
public class CartUtilityService {

    @Autowired
    private CartDao cartDao;

    @Autowired
    private ProductUtilityService productUtilityService;


    @Autowired
    private UserDao userDao;



//    @Transactional
//    public void createCart(UUID userId){
//
//        Cart cart = Cart
//                .builder()
//                .customerId(userId)
//                .build();
//    }


//    @Transactional
//    public void createCart(UserCartDto userCartDto) throws ExecutionException, InterruptedException {
//
//        CompletableFuture<Optional<Product>> productFromDb = productUtilityService.findProduct(userCartDto.getProductId());
//
//        if(productFromDb.get().isEmpty())throw new NoSuchElementException();
//
//
//        Invoice invoice = Invoice
//                .builder()
//               // .totalProductCost(productFromDb.get().get().getProductCost() * userCartDto.getQty())
//                .offer(0)
//                .platformFee(0.5)
//                .handlingCost(5)
//                .deliveryFee(3)
//                .build();
//
//        Cart cart = Cart
//                .builder()
//                .customerId(userCartDto.getUserId())
//                .darkStoreId(userCartDto.getStoreId())
//                .itemList(
//                        List.of(
//                                Item.builder()
//                                        .product(productFromDb.get().get())
//                                        .qty(userCartDto.getQty())
//                                        .build()
//                        )
//                )
//                .invoice(invoice)
//                .build();
//
//
//        cartDao.saveCart(cart);
//    }
//
//
//    @Transactional
//    public void updateCart(UserCartDto userCartDto) throws ExecutionException, InterruptedException{
//
//        CompletableFuture<Optional<Cart>> userOptional =  cartDao.findCartByUserId(userCartDto.getUserId());
//
//        if(userOptional.get().isEmpty()){
//            createCart(userCartDto);
//        }else{
//            updateItemCart(userCartDto,userOptional.get().get());
//        }
//    }
//
//
//
//    @Transactional
//    public void updateItemCart(UserCartDto userCartDto, Cart cart) throws ExecutionException, InterruptedException {
//
//        // Fetch the product asynchronously
//        CompletableFuture<Optional<Product>> productFromDbFuture = productUtilityService.findProduct(userCartDto.getProductId());
//
//        Optional<Product> productFromDb = productFromDbFuture.get();
//
//        if (productFromDb.isEmpty()) {
//            throw new NoSuchElementException("Product with ID " + userCartDto.getProductId() + " not found.");
//        }
//
//        Product product = productFromDb.get();
//
//        // Map the cart items by product ID for quick lookup
//        Map<UUID, Item> itemMap = cart.getItemList().stream()
//                .collect(Collectors.toMap(item -> item.getProduct().getProductId(), item -> item));
//
//        if (itemMap.containsKey(product.getProductId())) {
//            Item existingItem = itemMap.get(product.getProductId());
//
//            if (userCartDto.getQty() <= 0) {
//                cart.getInvoice().setTotalProductCost(
//                        cart.getInvoice().getTotalProductCost()
//                                - (existingItem.getProduct().getProductCost() * existingItem.getQty())
//                );
//                cart.getItemList().remove(existingItem);
//            } else if (existingItem.getQty() != userCartDto.getQty()) {
//                cart.getInvoice().setTotalProductCost(
//                        cart.getInvoice().getTotalProductCost()
//                                - (existingItem.getProduct().getProductCost() * existingItem.getQty())
//                                + (userCartDto.getQty() * existingItem.getProduct().getProductCost())
//                );
//
//                existingItem.setQty(userCartDto.getQty());
//            }
//        } else if (userCartDto.getQty() > 0) {
//            // New item, add it to the cart
//            Item newItem = Item.builder()
//                    .product(product)
//                    .qty(userCartDto.getQty())
//                    .build();
//
//            cart.getInvoice().setTotalProductCost(
//                    cart.getInvoice().getTotalProductCost()
//                            + (userCartDto.getQty() * newItem.getProduct().getProductCost())
//            );
//
//            cart.getItemList().add(newItem);
//        }
//
//        cartDao.saveCart(cart);
//    }

    @Transactional
    public void deleteCart(UUID cartId){
        cartDao.deleteCart(cartId);
    }


    public void incrementItem(UUID productId, UUID userId){

        User user = userDao.findUserById(userId);

        if(userId == null)throw new NoSuchElementException();

        Cart cart = user.getCart();




    }



}
