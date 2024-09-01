package org.example.inventory_write_service.controller;


import lombok.extern.slf4j.Slf4j;
import org.example.inventory_write_service.dto.*;
import org.example.inventory_write_service.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/inventory-service/inventory/v1")
@Slf4j
public class InventoryController {

    @Autowired
    private InventoryService inventoryService;


    @Autowired
    private SubCategoryService subCategoryService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private SubCategoryProductService subCategoryProductService;

    @Autowired
    private ManufacturerService manufacturerService;

    @Autowired
    private ProductService productService;


//    Category Controller

    @PostMapping("/create/category")
    public ResponseEntity<String> createCategory(@RequestBody CategoryC categoryC){
        try {
            categoryService.createCategory(categoryC);
            return ResponseEntity.ok("Success");
        }catch (Exception e){
            log.error(e.toString());
            return ResponseEntity.badRequest().body(e.getMessage());

        }
    }

    @PatchMapping("/update/category")
    public ResponseEntity<String> updateCategoryPartially(@RequestBody CategoryDto categoryDto){

        try {
            categoryService.updateCategory(categoryDto);
            return ResponseEntity.ok("Success");
        }catch (Exception e){
            log.error(e.toString());
            return ResponseEntity.badRequest().body(e.getMessage());

        }

    }

    @DeleteMapping("/delete/category")
    public ResponseEntity<String> deleteCategory(@RequestParam UUID categoryId){

        try {
            categoryService.deleteCategoryCondition(categoryId);
            return ResponseEntity.ok("Success");
        }catch (Exception e){
            log.error(e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

//    SubCategory Controller


    @PostMapping("/create/sub-category")
    public ResponseEntity<String> createSubCategory(@RequestBody SubCategoryC subCategoryc){

        try {
            subCategoryService.createSubCategory(subCategoryc);
            return ResponseEntity.ok("Success");
        }catch (Exception e){
            log.error(e.toString());
            return ResponseEntity.badRequest().body(e.getMessage());

        }
    }

    @PatchMapping("/update/sub-category")
    public ResponseEntity<String> updateSubCategory(@RequestBody SubCategoryC subCategoryC, @RequestParam UUID subId){
        try {
            subCategoryService.updateSubCategory(subCategoryC,subId);
            return ResponseEntity.ok("Success");
        }catch (Exception e){
            log.error(e.toString());
            return ResponseEntity.badRequest().body(e.getMessage());

        }
    }

    @DeleteMapping("/delete/sub-category")
    public ResponseEntity<String> deleteSubCategory(@RequestParam UUID subCategoryId){

        try {
            subCategoryService.deleteSubCategory(subCategoryId);
            return ResponseEntity.ok("Success");
        }catch (Exception e){
            log.error(e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    // sub-product-category

    @PostMapping("/create/sub-product-category")
    public ResponseEntity<String> createSubProductCategory(@RequestBody SubProductCategoryC subProductCategoryC){

        try {
            subCategoryProductService.createSubProductCategory(subProductCategoryC);
            return ResponseEntity.ok("Success");
        }catch (Exception e){
            log.error(e.toString());
            return ResponseEntity.badRequest().body(e.getMessage());

        }
    }

    @PatchMapping("/update/sub-product-category")
    public ResponseEntity<String> updateSubProductCategory(@RequestBody SubProductCategoryC subProductCategoryC, @RequestParam UUID id){

        try {
            subCategoryProductService.updateSubCategoryProduct(subProductCategoryC,id);
            return ResponseEntity.ok("Success");
        }catch (Exception e){
            log.error(e.toString());
            return ResponseEntity.badRequest().body(e.getMessage());

        }

    }

    @DeleteMapping("/delete/sub-product-category")
    public ResponseEntity<String> deleteSubProductCategory(@RequestParam UUID subProductCategoryId){
        try {
            subCategoryProductService.deleteSubProductCategory(subProductCategoryId);
            return ResponseEntity.ok("Success");
        }catch (Exception e){
            log.error(e.toString());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

//    Manufacturer

    @PostMapping("/create/manufacturer")
    public ResponseEntity<String> createManufacturer(@RequestBody ManufacturerC manufacturerC){

        try {
            manufacturerService.createManufacturer(manufacturerC);
            return ResponseEntity.ok("Success");
        }catch (Exception e){
            log.error(e.toString());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/update/manufacturer")
    public ResponseEntity<String> updateManufacturer(@RequestBody ManufacturerDto manufacturerDto){
        try {
            manufacturerService.updateManufacturer(manufacturerDto);
            return ResponseEntity.ok("Success");

        }
        catch (Exception e){
            log.error(e.toString());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @PostMapping("/add/items")
    public ResponseEntity<String> addItemToInventory(@RequestBody ItemC itemC){
        try {
            productService.createProduct(itemC);
            return ResponseEntity.ok("Success");
        }catch (Exception e){
            log.error(e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    @PatchMapping ("/update/items")
    public ResponseEntity<String> updateItemToInventory(
            @RequestBody UpdateProductDto updateProductDto ,
            @RequestParam UUID productId){
        try {

            productService.updateProduct(updateProductDto,productId);
            return ResponseEntity.ok("Success");

        }catch (Exception e){
            log.error(e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    @PatchMapping("/update/items-cost")
    public ResponseEntity<String> updateItemCostToInventory(@RequestParam UUID productId, @RequestParam int price, @RequestParam int offer){
        try {
            productService.updatePrice(productId,price,offer);
            return ResponseEntity.ok("Success");
        }catch (Exception e){
            log.error(e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }







}
