package org.example.elasticsearch.component;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.example.elasticsearch.dto.ProductDto;
import org.example.elasticsearch.dto.ProductSearchDto;
import org.example.elasticsearch.model.Event;
import org.example.elasticsearch.model.EventType;
import org.example.elasticsearch.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.UUID;

@Component
@Slf4j
public class KafkaDistributorConsumer {

    @Autowired
    private ProductService productService;

    public void aggregate(Event event) throws Exception {


        switch ( event.getEntity() ){

            case "search-db" -> {

                try {

                    if (event.getEventType() == EventType.CREATE) {

                        ProductSearchDto productSearchDto = deserialize(event.getObject(), ProductSearchDto.class);

                        if (productSearchDto != null && productSearchDto.getProductId() != null) {
                            productService.createUser(ProductDto
                                    .builder()
                                    .productId(productSearchDto.getProductId())
                                    .productName(productSearchDto.getProductName())
                                    .productCategory(productSearchDto.getProductCategory())
                                    .productDescription(productSearchDto.getProductDescription())
                                    .productSubCategory(productSearchDto.getProductSubCategory())
                                    .productSubProductCategory(productSearchDto.getProductSubProductCategory())
                                    .manufacturerName(productSearchDto.getManufacturerName()
                                    ).build()
                            );
                        } else {
                            throw new RuntimeException("NULL VALUES");
                        }
                    }
                }catch (Exception e){
                    log.error(e.getLocalizedMessage());
                }

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
