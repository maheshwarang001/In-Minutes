package org.example.inventory_read_service.dao;


import org.example.inventory_read_service.entity.inventory.product.Manufacturer;
import org.example.inventory_read_service.entity.inventory.product.Product;

import org.example.inventory_read_service.entity.inventory.subProductCategory.SubProductCategory;
import org.example.inventory_read_service.repository.ProductRepository;
import org.example.inventory_read_service.service.ManufacturerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class ProductDao {

    @Autowired
    private ProductRepository productRepository;


    @Autowired
    private SubProductCategoryDao subProductCategoryDao;

    @Autowired
    private ManufacturerService manufacturerService;



    public Optional<Product> getProductById(UUID id){
        return productRepository.getProduct(id);
    }

    public void delete(UUID productId){
        productRepository.deleteById(productId);
    }

    public void updateProduct(Product product){

        productRepository.save(product);
    }


    public void addProductToInventory(Product itemC) throws Exception{

        try {

            Optional<SubProductCategory> subProductCategory = subProductCategoryDao.findBySubProductCategoryId(itemC.getSubProductCategory().getSubproductcategory_id());

            if (subProductCategory.isEmpty()){
                //subProductCategoryService.helperCreateSubProductCategory(itemC.getSubProductCategory());
            }

            Optional<Manufacturer> optionalManufacturer = manufacturerService.findById(itemC.getProduct_manufacturer().getManufacturer_id()) ;

            if (optionalManufacturer.isEmpty()){
                manufacturerService.helperCreateManufacturer(itemC.getProduct_manufacturer());
            }else {

                productRepository.save(itemC);
            }


        }catch (Exception e){
            throw  e;


        }
    }

    public List<Product> getAllProducts(){
        return productRepository.getAllProduct();
    }



}
