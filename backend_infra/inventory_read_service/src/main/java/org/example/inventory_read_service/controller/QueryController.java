package org.example.inventory_read_service.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.inventory_read_service.dto.CategoryDto;
import org.example.inventory_read_service.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/inventory-read/v1")
@Slf4j
public class QueryController {


    @Autowired
    private CategoryService categoryService;

    @GetMapping("/read/category/all")
    public ResponseEntity<List<CategoryDto>> readAllCategoryOrder(){

        try {
            List<CategoryDto> response = categoryService.readCategoryList();

            return ResponseEntity.ok().body(response);
        }catch (Exception e){
            log.error(e.getMessage());
            return ResponseEntity.badRequest().body(null);
        }

    }




}
