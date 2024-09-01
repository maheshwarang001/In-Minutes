package org.example.cartservice.Consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.example.cartservice.dto.Event;

import org.example.cartservice.dto.EventType;
import org.example.cartservice.dto.ProductCartDto;
import org.example.cartservice.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Slf4j
public class MessageConsumer {


    @Autowired
    private ProductService productService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    public void aggregate(Event event) {


        if(event.getEntity() != null){


            switch (event.getEntity()){


                case "product"->{

                    ProductCartDto productCartDto = deserialize(event.getObject(), ProductCartDto.class);

                    if(productCartDto != null){

                        if(event.getEventType().equals(EventType.CREATE)){
                            productService.createProduct(productCartDto);

                        } else if (event.getEventType().equals(EventType.UPDATE)) {

                            productService.updateProduct(productCartDto);
                        }
                        else {
                            log.info("wild card -> {}", event.getEntity() + event.getTimeStamp());
                        }

                    }else{
                        log.error("product cart kafka null");
                    }


                }
                default -> {
                    log.error("cart kafka null");
                }

            }
        }




    }

    private <T> T deserialize(byte[] bytes, Class<T> tClass) {
        try {
            return objectMapper.readValue(bytes, tClass);
        } catch (IOException e) {
            log.error("Json processing failed for object: {}", new String(bytes), e);
            return null;
        }
    }
}
