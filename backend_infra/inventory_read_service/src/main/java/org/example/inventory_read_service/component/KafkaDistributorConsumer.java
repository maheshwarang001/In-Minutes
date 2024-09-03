package org.example.inventory_read_service.component;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.example.inventory_read_service.dto.*;
import org.example.inventory_read_service.service.*;
import org.example.inventory_read_service.entity.inventory.category.Category;
import org.example.inventory_read_service.entity.inventory.product.Manufacturer;
import org.example.inventory_read_service.entity.inventory.product.Product;
import org.example.inventory_read_service.entity.inventory.subProductCategory.SubProductCategory;
import org.example.inventory_read_service.entity.inventory.subcategory.SubCategory;
import org.example.inventory_read_service.entity.store.StoreProduct;
import org.example.inventory_read_service.model.Event;
import org.example.inventory_read_service.model.EventType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.UUID;

@Component
@Slf4j
public class KafkaDistributorConsumer {


    @Autowired
    private StoreProductService storeProductService;
    @Autowired
    private CategoryService categoryService;

    @Autowired
    private SubCategoryService subCategoryService;

    @Autowired
    private SubProductCategoryService subProductCategoryService;

    @Autowired
    private ManufacturerService manufacturerService;


    @Autowired
    private ProductService productService;


    @Autowired
    private StoreService storeService;


    public void aggregate(Event event) throws Exception {


        switch ( event.getEntity() ){

            case "category" -> {

                if(event.getEventType() == EventType.CREATE){
                    CategoryDto category = deserialize(event.getObject(), CategoryDto.class);
                    categoryService.createCategory(category);
                }

                else if(event.getEventType() == EventType.UPDATE){
                    CategoryDto category = deserialize(event.getObject(), CategoryDto.class);
                    categoryService.updateCategory(category);
                }

                else if(event.getEventType() == EventType.DELETE){
                    UUID categoryId = deserialize(event.getObject(), UUID.class);
                    categoryService.deleteCategory(categoryId);
                }

            }
            case "sub-category" -> {


                if(event.getEventType() == EventType.CREATE){
                    SubCategoryDto subCategoryDto = deserialize(event.getObject(), SubCategoryDto.class);
                    subCategoryService.createSubCategory(subCategoryDto);
                }

                else if(event.getEventType() == EventType.UPDATE){
                    SubCategoryDto subCategoryDto = deserialize(event.getObject(), SubCategoryDto.class);
                    subCategoryService.updateSubCategory(subCategoryDto);
                }

                else if(event.getEventType() == EventType.DELETE){
                    UUID subCategoryId = deserialize(event.getObject(), UUID.class);
                    subCategoryService.deleteSubCategory(subCategoryId);
                }


            }

            case "sub-product-category" ->{

                if(event.getEventType() == EventType.CREATE){
                    SubProductCategoryDto subProductCategoryDto = deserialize(event.getObject(), SubProductCategoryDto.class);
                    subProductCategoryService.createSubProductCategory(subProductCategoryDto);
                }
                else if(event.getEventType() == EventType.UPDATE){
                    SubProductCategoryDto subProductCategoryDto = deserialize(event.getObject(), SubProductCategoryDto.class);
                    subProductCategoryService.updateSubProductCategory(subProductCategoryDto);

                }
                else if(event.getEventType() == EventType.DELETE){
                    UUID subProductCategoryId = deserialize(event.getObject(), UUID.class);
                    subProductCategoryService.deleteSubProductCategory(subProductCategoryId);
                }
            }

            case "manufacturer" ->{

                if(event.getEventType() == EventType.CREATE){
                    ManufacturerDto manufacturerDto = deserialize(event.getObject(), ManufacturerDto.class);
                    manufacturerService.createManufacturer(manufacturerDto);

                }

                else if(event.getEventType() == EventType.UPDATE){
                    ManufacturerDto manufacturerDto = deserialize(event.getObject(), ManufacturerDto.class);
                    manufacturerService.updateManufacturer(manufacturerDto);
                }

                else if(event.getEventType() == EventType.DELETE){
//                    Manufacturer manufacturer = deserialize(event.getObject(), Manufacturer.class);
                    //implement left to delete address delete edge case
                }


            }
            case "product" ->{

                if(event.getEventType() == EventType.CREATE){
                    ProductDto productDto = deserialize(event.getObject() , ProductDto.class);
                    productService.createProduct(productDto);
                }

                else if(event.getEventType() == EventType.UPDATE){
                    ProductDto productDto = deserialize(event.getObject() , ProductDto.class);
                    productService.updateProduct(productDto);
                }
                else if(event.getEventType() == EventType.DELETE){
                    UUID productId = deserialize(event.getObject() , UUID.class);
//                    Implement delete
                }

            }
            case "store" ->{

                if(event.getEventType() == EventType.CREATE){
                    DarkStoreDto darkStoreDto = deserialize(event.getObject() , DarkStoreDto.class);
                    storeService.createDarkStore(darkStoreDto);
                }

            }

            case "store-product" ->{

                DarkStoreProductDto darkStoreProductDto = deserialize(event.getObject(),DarkStoreProductDto.class);
                storeProductService.updateProductInStore(darkStoreProductDto);

            }



        }


    }

    static <T> T deserialize(byte[] bytes, Class<T> tClass) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(bytes, tClass);
        } catch (IOException e) {
            log.error(String.format("Json processing failed for object: %s", bytes.toString()), e);
        }
        return null;
    }


}
