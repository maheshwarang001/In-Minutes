package org.example.cartuserservice.service;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.example.cartuserservice.dao.*;
import org.example.cartuserservice.dto.*;
import org.example.cartuserservice.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Component
@Slf4j
public class CartUtils {


    @Autowired
    private UserDao userDao;

    @Autowired
    private ProductDao productDao;

    @Autowired
    private CartDao cartDao;


    @Autowired
    private AddressDao addressDao;


    @Autowired
    private InvoiceDao invoiceDao;



    @Transactional
    public void incrementProductInCart(UUID productId, UUID userId, UUID storeId){


        User user = userDao.findUserById(userId);

        if(user != null){

            Cart cart = user.getCart();

            if(cart == null){
                cart = Cart.builder().build();
                user.setCart(cart);
                cartDao.saveCart(cart);
            }

            Product product = productDao.productListForCartAndPid(cart.getCartId(),productId);

            Product updatedProduct;

            if(product == null) {

                updatedProduct =Product
                        .builder()
                        .productId(productId)
                        .qty(1)
                        .cart(cart)
                        .build();

            }else{

                updatedProduct = product;
                updatedProduct.setQty(product.getQty() + 1);
            }

            //ProductDetailDto qtycheck = productDetailsFromInventory(QueryProduct.builder().storeId(storeId).productId(productId).build());

            productDao.saveProduct(updatedProduct);

        }else throw new IllegalArgumentException();
    }

    private ProductDetailDto productDetailsFromInventory(QueryProduct queryProduct){

        try {

            RestTemplate restTemplate = new RestTemplate();

            return restTemplate.postForObject(
                    "",
                    queryProduct,
                    ProductDetailDto.class

            );

        }catch (Exception e){
            log.error(e.getLocalizedMessage());
            throw e;
        }
    }



    @Transactional
    public void decrementProductCart(UUID productId, UUID userId) {

        User user = userDao.findUserById(userId);

        if (user == null) {
            throw new IllegalArgumentException("User not found with ID: " + userId);
        }

        Cart cart = user.getCart();

        if (cart == null) {
            throw new NoSuchElementException("No cart associated with user ID: " + userId);
        }

        Product product = productDao.productListForCartAndPid(cart.getCartId(), productId);

        if (product == null) {
            throw new NoSuchElementException("Product not found in cart for product ID: " + productId);
        }

        if (product.getQty() <= 1) {
            productDao.delete(product.getTableId());
        } else {
            product.setQty(product.getQty() - 1);
            productDao.saveProduct(product);
        }
    }


    @Transactional
    public EstimateDto provideEstimateDto(UUID userId, long addressId) throws ExecutionException, InterruptedException {
        // Fetch user and address asynchronously
        CompletableFuture<User> userFuture = CompletableFuture.supplyAsync(() -> userDao.findUserById(userId));
        CompletableFuture<Address> addressFuture = CompletableFuture.supplyAsync(() -> addressDao.getAddressByAddressId(addressId));

        // Fetch products after user data is available
        CompletableFuture<List<Product>> productsFuture = userFuture.thenCompose(user -> {
            Cart cart = user.getCart();
            if (cart == null) throw new NoSuchElementException();
            return CompletableFuture.supplyAsync(() -> productDao.productListForCart(cart.getCartId()));
        });

        // Combine all futures and process results
        return CompletableFuture.allOf(userFuture, addressFuture, productsFuture)
                .thenApplyAsync(voidResult -> {
                    // Ensure that we join the futures within the transaction boundary
                    User user = userFuture.join();
                    List<Product> allProducts = productsFuture.join();
                    Address address = addressFuture.join();
                    UUID storeId = address.getNearestStore();

                    List<Item> items = allProducts.stream()
                            .map(it -> new Item(it.getProductId(), it.getQty()))
                            .collect(Collectors.toList());

                    QueryProduct queryProduct = QueryProduct.builder()
                            .storeId(storeId)
                            .items(items)
                            .build();

                    EstimateCheckCartDto estimateCheckCartDto = checkPayableAndQty(queryProduct);

                    updateProductsBasedOnItemDetails(estimateCheckCartDto.getProductDetailDtoList(), allProducts);

                    double total = 0.2 + 3.5 + 0.5;
                    double offer = 0;
                    double cost = estimateCheckCartDto.getInvoiceProduct().getTotalProductCost();

                    InvoiceDto invoice = InvoiceDto.builder()
                            .totalProductCost(cost)
                            .offer(offer)
                            .platformFee(0.2)
                            .deliveryFee(3.5)
                            .handlingCost(0.5)
                            .totalPayable(total + cost)
                            .build();


                    Invoice saveInvoice = Invoice.builder()
                            .totalProductCost(invoice.getTotalProductCost())
                            .offer(offer)
                            .platformFee(0.2)
                            .deliveryFee(3.5)
                            .handlingCost(0.5)
                            .totalPayable(total + cost)
                            .build();

                    // Save the invoice and update the cart within the transaction
                    invoiceDao.saveInvoice(saveInvoice);
                    user.getCart().setInvoice(saveInvoice);

                    EstimateDto estimateDto = EstimateDto.builder()
                            .cartId(user.getCart().getCartId())
                            .addressId(
                                    AddressOrder
                                            .builder()
                                            .addressId(address.getAddressId())
                                            .address(address.getAddress())
                                            .city(address.getCity())
                                            .postcode(address.getPostcode())
                                            .customerLocation(address.getCustomerLocation())
                                            .nearestStore(address.getNearestStore())
                                            .build()
                            )
                            .productDetailDtoList(estimateCheckCartDto.getProductDetailDtoList())
                            .invoiceDto(invoice)
                            .build();

                    return estimateDto;
                }).join();
    }

    @Transactional
    public void updateProductsBasedOnItemDetails(List<ItemDetailsDto> itemDetailsList, List<Product> productList) {

        Map<UUID, ItemDetailsDto> itemDetailsMap = itemDetailsList.stream()
                .collect(Collectors.toMap(ItemDetailsDto::getProductId, item -> item));

        // Use an iterator to safely remove products from the list while iterating
        Iterator<Product> productIterator = productList.iterator();

        while (productIterator.hasNext()) {
            Product product = productIterator.next();
            UUID productId = product.getProductId();

            if (itemDetailsMap.containsKey(productId)) {
                ItemDetailsDto itemDetails = itemDetailsMap.get(productId);

                if (itemDetails.getQty() == 0) {
                    // Delete the product if qty is 0
                    productDao.delete(product.getTableId());
                    productIterator.remove();
                } else {
                    // Update the product qty and save it if qty is not 0
                    product.setQty(itemDetails.getQty());
                    productDao.saveProduct(product);
                }
            } else {
                // Delete the product if it doesn't exist in the list of itemDetails
                productDao.delete(product.getTableId());
                productIterator.remove();
            }
        }
    }




    public EstimateCheckCartDto checkPayableAndQty(@NonNull QueryProduct queryProduct){


        try {


            RestTemplate restTemplate = new RestTemplate();

            ResponseEntity<EstimateCheckCartDto> responseEntity = restTemplate.postForEntity(
                    "",
                    queryProduct,
                    EstimateCheckCartDto.class
            );

            if (responseEntity.getStatusCode() == HttpStatus.OK) {
                return responseEntity.getBody();

            } else {
                throw new RuntimeException();
            }
        }catch (Exception e){


            log.error(e.getLocalizedMessage());
            throw e;
        }


    }

}
