package org.example.inventory_write_service.dao;


import org.example.inventory_write_service.entity.inventory.product.Manufacturer;
import org.example.inventory_write_service.entity.inventory.product.Product;
import org.example.inventory_write_service.entity.inventory.product.ProductCost;
import org.example.inventory_write_service.entity.inventory.product.ProductDetails;
import org.example.inventory_write_service.entity.inventory.subProductCategory.SubProductCategory;
import org.example.inventory_write_service.dto.ItemC;
import org.example.inventory_write_service.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

@Component
public class ProductDao {

    @Autowired
    private ProductRepository productRepository;


    @Autowired
    private CategoryDao categoryDao;

    @Autowired
    private SubCategoryDao subCategoryDao;

    @Autowired
    private SubProductCategoryDao subProductCategoryDao;

    @Autowired
    private ManufacturerDao manufacturerDao;





    public void saveProduct(Product product){
        productRepository.save(product);
    }





    public List<Product> getAllProducts(){
        return productRepository.getAllProduct();
    }

    public int subProductCategoryDependency(UUID subProductCategoryId){
        return productRepository.findSubProductCategoryDependency(subProductCategoryId);
    }

    public Optional<Product> findProductById(UUID pId){
        return productRepository.findProductById(pId);
    }

}
