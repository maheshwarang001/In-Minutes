package org.example.websocket.Consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import org.example.websocket.dto.Event;
import org.example.websocket.dto.OrderRiderDto;
import org.example.websocket.websocketconfig.JobScheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Slf4j
public class MessageConsumer {

    @Autowired
    JobScheduler jobScheduler;

    private final ObjectMapper objectMapper = new ObjectMapper();

    public void aggregate(Event event) {

        try {


            switch (event.getName()){

                case "rider-order-broadcast"->{


                    OrderRiderDto orderRiderDto = deserialize(event.getMessage(), OrderRiderDto.class);

                    if(orderRiderDto != null){
                        jobScheduler.sendOrderTicketToRider(orderRiderDto);
                    }

                }
            }



        } catch (Exception e) {
            log.error("Error processing event: {}", event, e);
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
