package org.example.elasticsearch.dao;

import org.example.elasticsearch.entity.Product;
import org.example.elasticsearch.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.client.elc.NativeQueryBuilder;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ElasticQueryDao {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    public List<Product> findProductByFuzzySearch(String searchKeyword) {

        NativeQuery query = new NativeQueryBuilder()
                .withQuery(q -> q.multiMatch(m -> m
                        .fields("productName", "productDescription", "productCategory",
                                "productSubCategory", "productSubProductCategory", "manufacturerName")
                        .query(searchKeyword)
                        .fuzziness("AUTO")
                )
                )
                .build();

        return elasticsearchTemplate.search(query,Product.class)
                .map(SearchHit::getContent)
                .toList();
    }

    public List<Product> findProductByExactNameOrId(String searchKeyword) {
        NativeQuery query = new NativeQueryBuilder()
                .withQuery(q -> q.bool(b -> b
                        .should(s -> s
                                .term(t -> t
                                        .field("productName")
                                        .value(searchKeyword)
                                )
                        )

                        .should(s -> s
                                .term(t -> t
                                        .field("productId")
                                        .value(searchKeyword)
                                )
                        )
                ))
                .build();

        return elasticsearchTemplate.search(query, Product.class)
                .map(SearchHit::getContent)
                .toList();
    }


    public List<Product> findProductWithRangePriceSearch(String searchKeyword ,int min, int max){

        Criteria textSearchCriteria = new Criteria("productName").fuzzy(searchKeyword)
                .or(new Criteria("productDescription").fuzzy(searchKeyword))
                .or(new Criteria("productCategory").fuzzy(searchKeyword))
                .or(new Criteria("productSubCategory").fuzzy(searchKeyword))
                .or(new Criteria("productSubProductCategory").fuzzy(searchKeyword))
                .or(new Criteria("manufacturerName").fuzzy(searchKeyword));

        Criteria priceRangeCriteria = new Criteria("cost").between(min, max);

        Criteria combinedCriteria = textSearchCriteria.and(priceRangeCriteria);

        NativeQuery query = new NativeQueryBuilder()
                .withQuery(new CriteriaQuery(combinedCriteria))

                .build();

        return elasticsearchTemplate.search(query, Product.class)
                .map(SearchHit::getContent)
                .toList();

    }


}
