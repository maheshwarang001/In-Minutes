package org.example.inventory_write_service.service;

import lombok.extern.slf4j.Slf4j;
import org.example.inventory_write_service.Producer.KafkaMessageProducer;
import org.example.inventory_write_service.configuration.Topics;
import org.example.inventory_write_service.dao.ManufacturerDao;
import org.example.inventory_write_service.dao.ProductDao;
import org.example.inventory_write_service.dao.SubProductCategoryDao;
import org.example.inventory_write_service.dto.*;
import org.example.inventory_write_service.entity.inventory.product.Manufacturer;
import org.example.inventory_write_service.entity.inventory.product.Product;
import org.example.inventory_write_service.entity.inventory.product.ProductCost;
import org.example.inventory_write_service.entity.inventory.product.ProductDetails;
import org.example.inventory_write_service.entity.inventory.subProductCategory.SubProductCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

import static org.example.inventory_write_service.service.InventoryService.serialize;

@Service
@Slf4j
public class ProductService {

    final static String productEntityName = "product";

    @Autowired
    private KafkaMessageProducer kafkaMessageProducer;

    @Autowired
    private SubCategoryProductService subCategoryProductService;

    @Autowired
    private ManufacturerService manufacturerService;


    @Autowired
    private ProductDao productDao;

    @Transactional
    public void createProduct(ItemC itemC) throws Exception {
        try {
            Product product = helperAddProductToInventory(itemC);

            ProductDto productDto =  ProductDto.generateProductDtoFromObject(product);

            kafkaMessageProducer.sendMessage(
                    Event.builder()
                            .eventType(EventType.CREATE )
                            .entity(productEntityName)
                            .object(serialize(productDto))
                            .timeStamp(LocalDateTime.now())
                            .build()
                    ,
                    Topics.TOPIC_INVENTORY
            );

        }catch (Exception e){
            throw e;
        }
    }

    public Product helperAddProductToInventory(ItemC itemC) throws Exception{

        try {

            Optional<SubProductCategory> subProductCategory = subCategoryProductService.findSubProductCategoryById(itemC.getSubProductCategoryId());
            if (subProductCategory.isEmpty()) throw new NoSuchElementException("Sub-Category Doesn't Exist");


            Optional<Manufacturer> optionalManufacturer = manufacturerService.findManufactureById(itemC.getProductManufacturer_id()) ;
            if (optionalManufacturer.isEmpty()) throw new NoSuchElementException("Manufacturer is not valid");


            Product product = getProduct(itemC, optionalManufacturer, subProductCategory);

            productDao.saveProduct(product);
            return product;

        }catch (Exception e){
            throw  e;

        }

    }

    private static Product getProduct(ItemC itemC, Optional<Manufacturer> optionalManufacturer, Optional<SubProductCategory> subProductCategory) {
        ProductDetails productDetails = new ProductDetails(
                itemC.getItemDetail().getProductDescription(),
                itemC.getItemDetail().getUnit(),
                itemC.getItemDetail().getMetaData(),
                itemC.getItemDetail().getProductCountryOfOrigin()
        );

        ProductCost productCost = new ProductCost(
                itemC.getItemCost().getMrp()
        );


        return new Product(
                itemC.getProductName(),
                itemC.getProductPicture(),
                productDetails,
                productCost,
                false,
                optionalManufacturer.orElse(null),
                subProductCategory.orElse(null)
        );
    }

    @Transactional
    public void updateProduct(UpdateProductDto updateProductDto, UUID pId){
        try {


            Optional<Product> queryProduct = productDao.findProductById(pId);

            if(queryProduct.isPresent()){

                Product newProduct = queryProduct.get();

                Product modeledProduct = updateProductDto(updateProductDto,newProduct);

                productDao.saveProduct(modeledProduct);

                ProductDto productDto = ProductDto.generateProductDtoFromObject(modeledProduct);

                kafkaMessageProducer.sendMessage(
                        Event.builder()
                                .eventType(EventType.UPDATE )
                                .entity(productEntityName)
                                .object(serialize(productDto))
                                .timeStamp(LocalDateTime.now())
                                .build()
                        ,
                        Topics.TOPIC_INVENTORY
                );

            }else throw new NoSuchElementException();

        }catch (Exception e){
            throw e;
        }
    }



    public  void updatePrice( UUID pId , int updatedPrice, int updateOffer){

        Optional<Product> product = productDao.findProductById(pId);
        if(product.isPresent()) {
            if (updatedPrice >= 0) {
                product.get().getProduct_cost().setMrp(updatedPrice);
            }
            if (updateOffer >= 0) {
                product.get().getProduct_cost().setOffer(updateOffer);
            }

            productDao.saveProduct(product.get());

            ProductDto productDto = ProductDto.generateProductDtoFromObject(product.get());

            kafkaMessageProducer.sendMessage(
                    Event.builder()
                            .eventType(EventType.UPDATE_PRODUCT_COST )
                            .entity(productEntityName)
                            .object(serialize(productDto))
                            .timeStamp(LocalDateTime.now())
                            .build()
                    ,
                    Topics.TOPIC_INVENTORY
            );

        }
        else throw new NoSuchElementException();

    }


    private Product updateProductDto(UpdateProductDto updateProductDto , Product product){

        if(updateProductDto.getProductName().isPresent()){
            product.setProduct_name(updateProductDto.getProductName().get().trim().toLowerCase());
        }

        if(updateProductDto.getProductPicture().isPresent()){
            product.setProduct_picture(updateProductDto.getProductPicture().get());
        }

        if(updateProductDto.getIsActive().isPresent() != product.isActive()){
            product.setActive(updateProductDto.getIsActive().get());
        }

        if(updateProductDto.getProductManufacturerId().isPresent()){
            Optional<Manufacturer> manufacturer = manufacturerService.findManufactureById(updateProductDto.getProductManufacturerId().get());
            if(manufacturer.isPresent()){
                product.setProduct_manufacturer(manufacturer.get());
            }else throw new NoSuchElementException();
        }

        if(updateProductDto.getSubProductCategoryId().isPresent()){
            Optional<SubProductCategory> subProductCategory = subCategoryProductService.findSubProductCategoryById(updateProductDto.getSubProductCategoryId().get());
            if (subProductCategory.isEmpty()) throw new NoSuchElementException("Sub-Category Doesn't Exist");
            else product.setSubProductCategory(subProductCategory.get());
        }

        if(updateProductDto.getProductDetails().isPresent()){
            UpdateProductDetailsDto updateProductDetailsDto = updateProductDto.getProductDetails().get();

            if(updateProductDetailsDto.getProductDescription().isPresent()){
                product.getProduct_details().setProduct_description(updateProductDetailsDto.getProductDescription().get());
            }
            if(updateProductDetailsDto.getProductCountryOfOrigin().isPresent()){
                product.getProduct_details().setCounty_origin(updateProductDetailsDto.getProductCountryOfOrigin().get());
            }
            if(updateProductDetailsDto.getUnit().isPresent()){
                product.getProduct_details().setUnit(updateProductDetailsDto.getUnit().get());
            }

            if(updateProductDetailsDto.getMetaData().isPresent()){
                //update later
            }

        }

        if(updateProductDto.getProductCost().isPresent()){
            UpdateProductCost updateProductCost = updateProductDto.getProductCost().get();

            if(updateProductCost.getMrp().isPresent()){
                product.getProduct_cost().setMrp(updateProductCost.getMrp().get());
            }
        }
        return product;

    }




}
