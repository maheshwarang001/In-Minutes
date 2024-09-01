package org.example.cartservice.service;

import org.example.cartservice.dao.ProductCartDao;
import org.example.cartservice.dto.ProductCartDto;
import org.example.cartservice.entity.Product;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.file.FileAlreadyExistsException;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Service
public class ProductUtilityService {

    @Autowired
    private ProductCartDao productCartDao;

    @Transactional
    public void createProduct(ProductCartDto productCartDto) {
        CompletableFuture<Optional<Product>> optionalCompletableFuture = productCartDao.findProductByOptional(productCartDto.getProductId());

        try {

            Optional<Product> optionalProduct = optionalCompletableFuture.get();

            if (optionalProduct.isEmpty()) {
                Product product = Product.builder()
                        .productId(productCartDto.getProductId())
                        .productName(productCartDto.getProductName())
                        .productImage(productCartDto.getProductImage())
                        .build();

                productCartDao.save(product);
            } else {
                throw new FileAlreadyExistsException("Product with ID " + productCartDto.getProductId() + " already exists.");
            }
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException("Failed to check product existence.", e);
        } catch (FileAlreadyExistsException e) {
            throw new RuntimeException(e);
        }
    }


    public CompletableFuture<Optional<Product>> findProduct(UUID uuid){
        return  productCartDao.findProductByOptional(uuid);
    }

    @Transactional
    public void updateProduct(ProductCartDto productCartDto) {
        CompletableFuture<Optional<Product>> optionalCompletableFuture = productCartDao.findProductByOptional(productCartDto.getProductId());

        try {
            Optional<Product> optionalProduct = optionalCompletableFuture.get();

            if (optionalProduct.isPresent()) {
                Product product = optionalProduct.get();

                if (product != null) {

                    if (productCartDto.getProductName() != null && !productCartDto.getProductName().trim().isEmpty()) {
                        product.setProductName(productCartDto.getProductName());
                    }

                    if (productCartDto.getProductImage() != null && !productCartDto.getProductImage().trim().isEmpty()) {
                        product.setProductImage(productCartDto.getProductImage());
                    }

                    if (productCartDto.getProductCost() != -1 && productCartDto.getProductCost() >= 0) {
                        product.setProductCost(productCartDto.getProductCost());
                    }

                    productCartDao.save(product);
                } else {
                    throw new RuntimeException("Product with ID " + productCartDto.getProductId() + " is null.");
                }
            } else {
                throw new RuntimeException("Product with ID " + productCartDto.getProductId() + " does not exist.");
            }
        } catch (InterruptedException | ExecutionException e) {
            Thread.currentThread().interrupt();  // Restore interrupted status
            throw new RuntimeException("Failed to check product existence.", e);
        }
    }
}
