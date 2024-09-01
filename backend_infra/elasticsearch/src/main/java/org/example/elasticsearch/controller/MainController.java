package org.example.elasticsearch.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.elasticsearch.dto.ProductDto;
import org.example.elasticsearch.entity.Product;
import org.example.elasticsearch.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@Slf4j
@RequestMapping("/elasticsearch/v1")
public class MainController {


    @Autowired
    private ProductService productService;

    public ResponseEntity<String> createItemManualIntervention(@RequestBody ProductDto productDto){

        try {

             productService.createUser(productDto);

            return ResponseEntity.ok().body("success");

        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getLocalizedMessage());
        }


    }

    public ResponseEntity<String> updateItemManualIntervention(@RequestBody ProductDto productDto, @RequestParam UUID id){
        try {
            productService.updateItem(productDto,id);
            return ResponseEntity.ok().body("success");

        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getLocalizedMessage());
        }

    }

    public ResponseEntity<String>deleteItemManually(@RequestParam UUID id){
        try {
            productService.deleteItem(id);
            return ResponseEntity.ok().body("success");

        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getLocalizedMessage());
        }
    }

    public ResponseEntity<List<Product>> searchByFuzzy(@RequestParam String searchKeyWord){
        try {
            return  ResponseEntity.ok().body(productService.searchByFuzzy(searchKeyWord));
        }catch (Exception e){
            return ResponseEntity.badRequest().body(null);

        }
    }

    public ResponseEntity<List<Product>> searchByFuzzyAndRange(@RequestParam String searchKeyWord, @RequestParam int min ,@RequestParam int max){
        try {
            return  ResponseEntity.ok().body(productService.searchByFuzzyRage(searchKeyWord,min, max));
        }catch (Exception e){
            return ResponseEntity.badRequest().body(null);

        }
    }
    public ResponseEntity<List<Product>> searchByExact(@RequestParam String searchKeyWord){
        try {
            return  ResponseEntity.ok().body(productService.searchByExact(searchKeyWord));
        }catch (Exception e){
            return ResponseEntity.badRequest().body(null);

        }
    }



}
