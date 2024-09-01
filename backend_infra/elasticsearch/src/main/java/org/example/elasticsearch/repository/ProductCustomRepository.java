//package org.example.elasticsearch.repository;
//
//import co.elastic.clients.elasticsearch._types.query_dsl.QueryBuilders;
//import co.elastic.clients.elasticsearch._types.query_dsl.MultiMatchQuery;
//import org.example.elasticsearch.entity.Product;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.elasticsearch.client.elc.ElasticsearchTemplate;
//import org.springframework.data.elasticsearch.client.elc.NativeQuery;
//import org.springframework.stereotype.Repository;
//
//import java.util.List;
//
//@Repository
//public class ProductCustomRepository {
//
//    @Autowired
//    private ElasticsearchTemplate elasticsearchTemplate;
//
//    public List<Product> searchProducts(String searchText) {
//        MultiMatchQuery multiMatchQuery = QueryBuilders.multiMatch()
//                .query(searchText)
//                .fields("productName", "productDescription", "productCategory", "productSubCategory", "manufacturerName")
//                .build();
//
//        NativeQuery searchQuery = NativeQuery.builder()
//                .withQuery(multiMatchQuery)
//                .build();
//
//        return elasticsearchTemplate.query(searchQuery, Product.class);
//    }
//}
