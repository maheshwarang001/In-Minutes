package org.example.elasticsearch.dao;

import org.example.elasticsearch.entity.Product;
import org.example.elasticsearch.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class ProductDao {


    @Autowired
    private ProductRepository productRepository;

    public void createItemManual(Product product){

        try {


            UUID id = UUID.randomUUID();

            while (checkIfPresentUUID(id)) {
                id = UUID.randomUUID();
            }

            product.setProductId(id);

        }catch (Exception e){
            throw e;
        }


    }

    public void deleteItem(UUID id){

        productRepository.deleteById(id);
    }

    public void updateProduct(Product product, UUID id) {
        try {
            Optional<Product> existingProductOpt = checkIfProductPresentUUID(id);

            if (existingProductOpt.isEmpty()) {
                throw new NoSuchElementException("Product not found");
            }

            Product existingProduct = existingProductOpt.get();

            if (product.getProductCategory() != null && !product.getProductCategory().isEmpty()) {
                existingProduct.setProductCategory(product.getProductCategory());
            }
            if (product.getProductDescription() != null && !product.getProductDescription().isEmpty()) {
                existingProduct.setProductDescription(product.getProductDescription());
            }
            if (product.getProductName() != null && !product.getProductName().isEmpty()) {
                existingProduct.setProductName(product.getProductName());
            }
            if (product.getProductSubProductCategory() != null && !product.getProductSubProductCategory().isEmpty()) {
                existingProduct.setProductSubProductCategory(product.getProductSubProductCategory());
            }
            if (product.isActive() != existingProduct.isActive()) { // Assuming `isActive` returns a Boolean
                existingProduct.setActive(product.isActive());
            }
            if (product.getManufacturerName() != null && !product.getManufacturerName().isEmpty()) {
                existingProduct.setManufacturerName(product.getManufacturerName());
            }

            if (product.getStoreInfoList() != null && !product.getStoreInfoList().isEmpty()) {
                Set<UUID> updatedStoreInfoList = new HashSet<>(existingProduct.getStoreInfoList());
                updatedStoreInfoList.addAll(product.getStoreInfoList());
                existingProduct.setStoreInfoList( new ArrayList<>(updatedStoreInfoList));
            }

            if (product.getCost() != -1) {
                existingProduct.setCost(product.getCost());
            }

        } catch (Exception e) {
            throw e;
        }
    }


    public boolean checkIfPresentUUID(UUID id){
        return productRepository.findProductByProductId(id).isPresent();
    }
    public Optional<Product> checkIfProductPresentUUID(UUID id){
        return productRepository.findProductByProductId(id);
    }

}
