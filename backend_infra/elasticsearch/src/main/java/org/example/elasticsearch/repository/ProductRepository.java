package org.example.elasticsearch.repository;

import org.example.elasticsearch.entity.Product;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProductRepository extends ElasticsearchRepository<Product, UUID> {


    Optional<Product> findProductByProductId(UUID id);

}
