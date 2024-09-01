package org.example.elasticsearch.service;


import org.example.elasticsearch.dao.ElasticQueryDao;
import org.example.elasticsearch.dao.ProductDao;
import org.example.elasticsearch.dto.ProductDto;
import org.example.elasticsearch.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class ProductService {


    @Autowired
    private ProductDao productDao;

    @Autowired
    private ElasticQueryDao elasticQueryDao;

    public void createUser(ProductDto product)throws  Exception{

        if(product.getProductName() == null) throw new NullPointerException();


        ProductDto productDto = new ProductDto();
        Product p = productDto.getProduct(productDto);

        productDao.createItemManual(p);

    }

    public void updateItem(ProductDto productDto, UUID id){

        try {

            ProductDto prodD = new ProductDto();
            Product p = productDto.getProduct(productDto);

            if(id == null) throw new NullPointerException();
            productDao.updateProduct(p,id);

        }catch (Exception e){
            throw e;
        }

    }

    public void deleteItem(UUID id){

        try {

            if(id == null) throw new NullPointerException();
            productDao.deleteItem(id);

        }catch (Exception e){
            throw e;
        }

    }

    public List<Product> searchByFuzzy(String keyword){
        try {
            return elasticQueryDao.findProductByFuzzySearch(keyword);
        }catch (Exception e){
            throw e;
        }
    }
    public List<Product> searchByFuzzyRage(String keyword, int min, int max){
        try {
            return elasticQueryDao.findProductWithRangePriceSearch(keyword,min, max);
        }catch (Exception e){
            throw e;
        }
    }

    public List<Product> searchByExact(String keyword){
        try {
            return elasticQueryDao.findProductByExactNameOrId(keyword);
        }catch (Exception e){
            throw e;
        }
    }




}
